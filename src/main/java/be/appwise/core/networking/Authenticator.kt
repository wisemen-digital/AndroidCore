package be.appwise.core.networking

import android.os.ConditionVariable
import android.text.TextUtils
import android.util.Log
import com.orhanobut.hawk.Hawk
import be.appwise.core.networking.NetworkConstants.HAWK_ACCESS_TOKEN_KEY
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.atomic.AtomicBoolean

class Authenticator(private val clientIdValue: String, private val clientSecretValue: String) : Authenticator {
    companion object {
        private val TAG = Authenticator::class.simpleName

        // this variable provides a way to sync refresh calls between multiple calls on different threads.
        private val LOCK = ConditionVariable(true)
        private val mIsRefreshing = AtomicBoolean(false)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request()

        val accessToken = Networking.getAccessToken()
        if (accessToken == null || TextUtils.isEmpty(accessToken.access_token)) {
            //just try the call without
            return request.newBuilder()
                .build()
        }

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

            if (!TextUtils.isEmpty(refreshToken)) {
                val newToken = Networking.getProtectedApiManager<ApiManagerService>()
                    ?.refreshToken(
                        NetworkConstants.REFRESH_TOKEN,
                    clientIdValue,
                    clientSecretValue,
                    refreshToken!!
                )?.execute()?.body()

                // refresh token
                if (newToken != null) {

                    return if (Networking.responseCount(response) >= 2) {
                        Log.d(TAG, "Logging out")
                        Networking.logout()
                        null
                    } else {
                        Log.d(TAG, "There is a new refresh token")
                        Networking.saveAccessToken(newToken)

                        Log.d(TAG, "new token is saved " + Hawk.get<String>(HAWK_ACCESS_TOKEN_KEY))

                        request.newBuilder()
                            .header(NetworkConstants.AUTHORIZATION, NetworkConstants.BEARER + newToken)
                            .build()
                    }
                }
            }
            LOCK.open()
            mIsRefreshing.set(false)
        } else {
            // Another thread is refreshing the token for us, let's wait for it.
            val conditionOpened = LOCK.block((7 * 1000).toLong())

            // If the next check is false, it means that the timeout expired, that is - the refresh
            // stuff has failed. The thread in charge of refreshing the token has taken care of
            // redirecting the user to the loginFlow activity.
            if (conditionOpened) {

                // another thread has refreshed this for us! thanks!
                val oauthTokenFromOtherThread = Hawk.get<String>(HAWK_ACCESS_TOKEN_KEY)
                val newAccessToken = if (oauthTokenFromOtherThread == null) "" else oauthTokenFromOtherThread + ""

                // sign the request with the new token and proceed
                val authRequestBuilder = request.newBuilder()
                authRequestBuilder.addHeader(NetworkConstants.AUTHORIZATION, NetworkConstants.BEARER + newAccessToken)
                // return the newly signed request
                return authRequestBuilder.build()
            }
        }

        return null
    }
}