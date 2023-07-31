package be.appwise.networking

import android.content.Context
import be.appwise.networking.model.AccessToken
import be.appwise.networking.model.BaseApiError
import retrofit2.Response

object Networking {
    @Volatile
    internal lateinit var appContext: Context
    private lateinit var networkingBuilder: NetworkingBuilder

    fun initialize(context: Context): Networking {
        appContext = context
        return this
    }

    fun init(function: NetworkingBuilder.() -> Unit) {
        networkingBuilder = NetworkingBuilder().apply(function)
        NetworkingBuilder().apply(function).build()
    }

    internal fun getContext() = appContext

    fun getAppName() = networkingBuilder.appName

    fun getVersionName() = networkingBuilder.versionName

    fun getVersionCode() = networkingBuilder.versionCode

    fun getApiVersion() = networkingBuilder.apiVersion

    fun getPackageName() = networkingBuilder.packageName

    fun getAccessToken() = networkingBuilder.getAccessToken()

    fun saveAccessToken(accessToken: AccessToken?) = networkingBuilder.saveAccessToken(accessToken)

    fun getClientIdValue() = networkingBuilder.clientId

    fun getClientSecretValue() = networkingBuilder.clientSecret

    /**
     * This logout function can be used to cleanup any resources the app is using.
     * i.e. remove all entries from Hawk, delete all data from Room, ...
     *
     * After that it will call a Deeplink in the app to return to the 'Starting Activity' without any backstack.
     * For this to work, don't forget to add the intent filter to your 'Starting Activity' to make the deep
     *
     * ```
     *    <intent-filter>
     *        <action android:name="${applicationId}.logout" />
     *        <category android:name="android.intent.category.DEFAULT" />
     *    </intent-filter>
     * ```
     */
    fun logout() {
        networkingBuilder.logout()
    }

    fun parseError(response: Response<*>): BaseApiError {
        return networkingBuilder.parseError(response)
    }
}