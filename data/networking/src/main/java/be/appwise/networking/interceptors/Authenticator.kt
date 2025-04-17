package be.appwise.networking.interceptors

import be.appwise.networking.NetworkConstants
import be.appwise.networking.Networking
import be.appwise.networking.NetworkingUtil.responseCount
import be.appwise.networking.model.AccessToken
import com.orhanobut.logger.Logger
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class Authenticator(
    private val onRefreshToken: (refreshToken: String) -> AccessToken?
) : Authenticator {
    private var callsWithoutToken = 0

    private fun extractBearerToken(authHeader: String?): String? {
        return authHeader?.takeIf { it.startsWith("Bearer ", ignoreCase = true) }
            ?.substring("Bearer ".length)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val originalRequest = response.request
        val requestUrl = originalRequest.url
        Logger.t("Authenticator").d("Authenticate triggered for $requestUrl")

        val failedToken = extractBearerToken(
            originalRequest.header(NetworkConstants.HEADER_KEY_AUTHORIZATION)
        )


        if (failedToken.isNullOrEmpty()) {
            // just try the call without

            //TODO: there is still something wrong with the way we handle this...
            // Because the client is only created once in the application's lifecycle the counter is persisted...
            callsWithoutToken++
            return if (callsWithoutToken >= 2) null else originalRequest
        }

        // This block tries to refresh the token and then retry the call with the new token
        // the 'synchronized' means that when other calls try to refresh the token as well they will wait until the first refresh happened
        //
        // Look at http://tutorials.jenkov.com/java-concurrency/synchronized.html for more information
        synchronized(this) {
            val threadId = Thread.currentThread().id
            Logger.t("Authenticator").d("Synchronized [$threadId] Sync block entered for: $requestUrl")

            val currentToken = Networking.getAccessToken()
            val currentAccessToken = currentToken?.access_token

            // Check if the request was previously made as an authenticated request
            if (response.request.header(NetworkConstants.HEADER_KEY_AUTHORIZATION) == null) return null

            // If the token has changed since the request was made, use the new token
            if (currentAccessToken != failedToken) {
                Logger.t("Authenticator").i("[$threadId] Token already refreshed by another thread ($requestUrl). Current: $currentAccessToken, Failed with: $failedToken. Retrying with current token.")

                return originalRequest.newBuilder()
                    .header(
                        NetworkConstants.HEADER_KEY_AUTHORIZATION,
                        "${currentToken?.token_type} ${currentToken?.access_token}"
                    )
                    .build()
            }

            Logger.t("Authenticator").i("[$threadId] Token needs refresh ($requestUrl). Token that failed: $failedToken")

            // check response count
            if (responseCount(response) >= 1) {
                Logger.t("Authenticator").w("[$threadId] Retry limit (1 refresh attempt) reached for $requestUrl. Aborting refresh and logging out.")
                Networking.logout()
                return null
            }

            // check if we have a refresh token
            val refreshToken = currentToken.refresh_token
            if (refreshToken.isNullOrBlank()) {
                Logger.t("Authenticator").e("[$threadId] No valid refresh token available for $requestUrl. Cannot refresh. Logging out.")
                Networking.logout()
                return null
            }

            val updatedToken = onRefreshToken(refreshToken)

            if (updatedToken == null) {
                // refresh failed , maybe you can logout user
                // returning null is critical here , because if you do not return null
                // it will try to refresh token continuously like 1000 times.
                // also you can try 2-3-4 times by depending you before logging out your user
                Logger.t("Authenticator").e("[$threadId] onRefreshToken failed for $requestUrl. Logging out.")
                Networking.logout()
                return null
            } else {
                Logger.t("Authenticator").i("[$threadId] Refresh SUCCESS for $requestUrl. Saving new token: ${updatedToken.access_token}. Retrying request.")
                Networking.saveAccessToken(updatedToken)

                // Retry the request with the new token
                return originalRequest.newBuilder()
                    .header(
                        NetworkConstants.HEADER_KEY_AUTHORIZATION,
                        "${updatedToken.token_type} ${updatedToken.access_token}"
                    )
                    .build()
            }
        }
    }
}