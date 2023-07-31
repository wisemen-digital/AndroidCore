package be.appwise.networking

import android.content.Context
import be.appwise.networking.bagel.BagelNetworkDiscoveryManager
import be.appwise.networking.base.BaseNetworkingListeners
import be.appwise.networking.model.AccessToken
import be.appwise.networking.model.BaseApiError
import be.appwise.networking.util.HawkUtils
import be.appwise.proxyman.ProxyManNetworkDiscoveryManager
import retrofit2.Response

class NetworkingBuilder {
    internal var packageName: String = ""
    internal var clientSecret = ""
    internal var clientId = ""
    internal var appName = ""
    internal var versionName = ""
    internal var versionCode = ""
    internal var apiVersion = ""
    private var networkingListeners = BaseNetworkingListeners.DEFAULT

    fun setPackageName(packageName: String): NetworkingBuilder {
        this.packageName = packageName
        return this
    }

    fun setClientSecret(clientSecretValue: String): NetworkingBuilder {
        this.clientSecret = clientSecretValue
        return this
    }

    fun setClientId(clientIdValue: String): NetworkingBuilder {
        this.clientId = clientIdValue
        return this
    }

    fun setAppName(appName: String): NetworkingBuilder {
        this.appName = appName
        return this
    }

    fun setVersionName(versionName: String): NetworkingBuilder {
        this.versionName = versionName
        return this
    }

    fun setVersionCode(versionCode: String): NetworkingBuilder {
        this.versionCode = versionCode
        return this
    }

    fun setApiVersion(apiVersion: String): NetworkingBuilder {
        this.apiVersion = apiVersion
        return this
    }

    fun setNetworkingListeners(customNetworkingListeners: BaseNetworkingListeners): NetworkingBuilder {
        this.networkingListeners = customNetworkingListeners
        return this
    }

    fun getAccessToken() = HawkUtils.hawkAccessToken

    fun saveAccessToken(accessToken: AccessToken?) {
        HawkUtils.hawkAccessToken = accessToken
    }

    @Deprecated("Please start using Proxyman instead of Bagel")
    fun registerBagelService(context: Context): NetworkingBuilder {
        BagelNetworkDiscoveryManager.registerService(context)
        return this
    }

    fun registerProxymanService(
        context: Context,
        deviceName: String? = null,
        allowedServices: ArrayList<String> = arrayListOf(),
        isLoggingEnabled: Boolean = true
    ): NetworkingBuilder {
        ProxyManNetworkDiscoveryManager.registerService(
            context,
            deviceName,
            allowedServices,
            isLoggingEnabled
        )
        return this
    }

    fun logout() {
        networkingListeners.logout()
    }

    fun parseError(response: Response<*>): BaseApiError {
        return networkingListeners.parseError(response)
    }

    internal fun build() {

    }
}
