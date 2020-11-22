package be.appwise.core.networking.interceptors

import be.appwise.core.networking.NetworkConstants
import be.appwise.core.networking.Networking
import be.appwise.core.networking.NetworkingUtil
import com.orhanobut.logger.Logger
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

object Authenticator : Authenticator {
    private var callsWithoutToken = 0

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request()

        // Get the current AccessToken
        val accessToken = Networking.getAccessToken()?.access_token

        if (accessToken == null || accessToken.isNullOrEmpty()) {
            // just try the call without

            //TODO: there is still something wrong with the way we handle this...
            // Because the client is only created once in the application's lifecycle the counter is persisted...
            callsWithoutToken++
            return if (callsWithoutToken >= 2) null else request
        }

        // This block tries to refresh the token and then retry the call with the new token
        // the 'synchronized' means that when other calls try to refresh the token as well they will wait until the first refresh happened
        //
        // Look at http://tutorials.jenkov.com/java-concurrency/synchronized.html for more information
        synchronized(this) {
            Logger.t("Authenticator").d("Synchronized")

            // Get the current AccessToken, this might be different as the previous one because of the 'synchronized' method
            val newToken = Networking.getAccessToken()?.access_token

            // Check if the request made was previously made as an authenticated request
            if (response.request().header(NetworkConstants.HEADER_KEY_AUTHORIZATION) != null) {
                // If the token has changed since the request was made, use the new token
                if (newToken != accessToken) {
                    Logger.t("Authenticator").d("Same tokens")

                    return request.newBuilder()
                        .header(
                            NetworkConstants.HEADER_KEY_AUTHORIZATION,
                            NetworkConstants.BEARER + newToken
                        )
                        .build()
                }

                // In case this is the first time this line gets hit refresh the AccessToken
                val updatedToken = Networking.refreshTokenCall()

                if (updatedToken == null || NetworkingUtil.responseCount(response) >= 2) {
                    // refresh failed , maybe you can logout user
                    // returning null is critical here , because if you do not return null
                    // it will try to refresh token continuously like 1000 times.
                    // also you can try 2-3-4 times by depending you before logging out your user
                    Networking.logout()
                } else {
                    // Refreshing the token worked, save the new one
                    Networking.saveAccessToken(updatedToken)

                    // Retry the request with the new token
                    return request.newBuilder()
                        .header(
                            NetworkConstants.HEADER_KEY_AUTHORIZATION,
                            NetworkConstants.BEARER + updatedToken.access_token
                        )
                        .build()
                }
            }
        }

        return null
    }
}