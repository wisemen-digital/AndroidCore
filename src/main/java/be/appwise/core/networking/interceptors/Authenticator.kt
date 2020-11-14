package be.appwise.core.networking.interceptors

import android.os.ConditionVariable
import be.appwise.core.networking.NetworkConstants
import be.appwise.core.networking.Networking
import be.appwise.core.networking.NetworkingUtil
import be.appwise.core.util.HawkUtils
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.atomic.AtomicBoolean

object Authenticator : Authenticator {

    // this variable provides a way to sync refresh calls between multiple calls on different threads.
    private val LOCK = ConditionVariable(true)
    private val mIsRefreshing = AtomicBoolean(false)

    private var callsWithoutToken = 0
    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request()

        val accessToken = Networking.getAccessToken()
        if (accessToken == null || accessToken.access_token.isNullOrEmpty()) {
            // just try the call without

            //TODO: there is still something wrong with the way we handle this...
            // Because the client is only created once in the application's lifecycle the counter is persisted...
            callsWithoutToken++
            return if (callsWithoutToken >= 2) null else request
        }


        //TODO: this can be done better and more concise
        // For an example with a 'synchronized' method look at
        // https://blog.coinbase.com/okhttp-oauth-token-refreshes-b598f55dd3b2
        // or
        // https://objectpartners.com/2018/06/08/okhttp-authenticator-selectively-reauthorizing-requests/

        /*
          *  Because we send out multiple HTTP requests in parallel, they might all list a 401 at the same time.
          *  Only one of them should refresh the token, because otherwise we'd refresh the same token multiple times
          *  and that is bad. Therefore we have these two static objects, a ConditionVariable and a boolean. The
          *  first thread that gets here closes the ConditionVariable and changes the boolean flag.
          */
        if (mIsRefreshing.compareAndSet(false, true)) {
            LOCK.close()

            // we're the first here. let's refresh this token.
            // it looks like our token isn't valid anymore.
            val refreshToken = accessToken.refresh_token

            if (!refreshToken.isNullOrBlank()) {
                val newToken = Networking.refreshTokenCall()

                if (newToken == null || NetworkingUtil.responseCount(response) >= 2) {
                    // refresh failed , maybe you can logout user
                    // returning null is critical here , because if you do not return null
                    // it will try to refresh token continuously like 1000 times.
                    // also you can try 2-3-4 times by depending you before logging out your user
                    Networking.logout()
                } else {
                    Networking.saveAccessToken(newToken)

                    request.newBuilder()
                        .header(NetworkConstants.AUTHORIZATION, NetworkConstants.BEARER + newToken)
                        .build()
                }
            }
        } else {
            // Another thread is refreshing the token for us, let's wait for it.
            val conditionOpened = LOCK.block((7 * 1000).toLong())

            // If the next check is false, it means that the timeout expired, that is - the refresh
            // stuff has failed. The thread in charge of refreshing the token has taken care of
            // redirecting the user to the loginFlow activity.
            if (conditionOpened) {

                // another thread has refreshed this for us! thanks!
                val oauthTokenFromOtherThread = HawkUtils.hawkAccessToken?.access_token
                val newAccessToken =
                    if (oauthTokenFromOtherThread == null) "" else oauthTokenFromOtherThread + ""

                // sign the request with the new token and proceed
                val authRequestBuilder = request.newBuilder()
                authRequestBuilder.addHeader(
                    NetworkConstants.AUTHORIZATION,
                    NetworkConstants.BEARER + newAccessToken
                )
                // return the newly signed request
                return authRequestBuilder.build()
            }
        }

        return null
    }
}