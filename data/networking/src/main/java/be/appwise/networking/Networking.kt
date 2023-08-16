package be.appwise.networking

import android.content.Context
import be.appwise.networking.base.BaseNetworkingListeners
import be.appwise.networking.model.AccessToken
import be.appwise.networking.model.BaseApiError
import be.appwise.networking.util.HawkUtils
import be.appwise.proxyman.ProxyManNetworkDiscoveryManager
import retrofit2.Response

object Networking {
    @Volatile
    internal lateinit var appContext: Context
    private lateinit var networkingConfig: NetworkingConfig
    private lateinit var networkingListeners: BaseNetworkingListeners


    /**
     * Initialize the object with the Android Start Up library using the [NetworkingInitializer]
     *
     * @param context The context provided by the initializer
     * @return [Networking] The initialized object
     */
    internal fun initialize(context: Context): Networking {
        appContext = context
        return this
    }

    fun init(
        newNetworkingConfig: NetworkingConfig,
        newNetworkingListeners: BaseNetworkingListeners = BaseNetworkingListeners.DEFAULT,
        proxyManConfig: ProxyManConfig
    ) {
        networkingConfig = newNetworkingConfig
        networkingListeners = newNetworkingListeners

        if (proxyManConfig.enabled) {
            registerProxymanService(appContext, proxyManConfig)
        }
    }

    internal fun getContext() = appContext

    fun getAppName() = networkingConfig.appName

    fun getVersionName() = networkingConfig.versionName

    fun getVersionCode() = networkingConfig.versionCode

    fun getApiVersion() = networkingConfig.apiVersion

    fun getPackageName() = networkingConfig.packageName

    fun getClientIdValue() = networkingConfig.clientId

    fun getClientSecretValue() = networkingConfig.clientSecret

    fun getAccessToken() = HawkUtils.hawkAccessToken

    fun saveAccessToken(accessToken: AccessToken?) {
        HawkUtils.hawkAccessToken = accessToken
    }

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
        networkingListeners.logout()
    }

    fun parseError(response: Response<*>): BaseApiError {
        return networkingListeners.parseError(response)
    }

    fun registerProxymanService(
        context: Context,
        proxyManConfig: ProxyManConfig
    ) {
        ProxyManNetworkDiscoveryManager.registerService(
            context,
            proxyManConfig.deviceName,
            proxyManConfig.allowedServices,
            proxyManConfig.isLoggingEnabled
        )
    }
}