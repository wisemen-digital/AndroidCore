package be.appwise.core.networking

import android.content.res.Resources
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class HeaderInterceptor(
    private val appName: String,
    private val versionName: String,
    private val versionCode: String,
    private val apiVersion: String,
    private val applicationId: String,
    private val protected: Boolean
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()

        Locale.getDefault()
        val systemConfiguration = Resources.getSystem().configuration
        val locale = if (Build.VERSION.SDK_INT >= 24) systemConfiguration.locales.get(0) else Locale.getDefault()
        val languageCode = locale.language

        val requestBuilder = request!!.newBuilder()
        requestBuilder.addHeader(
            NetworkConstants.USER_AGENT,
            appName + "/" + versionName + "/" + versionCode + " (" + Build.MODEL + "; Android/" + Build.VERSION.SDK_INT + ")"
        )
        requestBuilder.addHeader(
            NetworkConstants.ACCEPT,
            NetworkConstants.ACCEPT_APPLICATION_JSON_VALUE
        )
        requestBuilder.addHeader(NetworkConstants.ACCEPT_LANGUAGE, languageCode)
        requestBuilder.addHeader(NetworkConstants.API_VERSION, apiVersion)
        requestBuilder.addHeader(
            NetworkConstants.APP_PLATFORM,
            NetworkConstants.ANDROID
        )
        requestBuilder.addHeader(NetworkConstants.APP_ID, applicationId)

        if (protected) {
            Networking.getAccessToken()?.let {
                requestBuilder.addHeader(
                    NetworkConstants.HEADER_AUTHORIZATION_KEY,
                    "${it.token_type} ${it.access_token}"
                )
            }
        }

        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }
}