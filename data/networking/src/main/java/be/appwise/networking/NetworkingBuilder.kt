package be.appwise.networking

import android.content.Context
import be.appwise.networking.bagel.BagelNetworkDiscoveryManager
import be.appwise.networking.base.BaseNetworkingListeners
import be.appwise.networking.model.AccessToken
import be.appwise.networking.model.BaseApiError
import be.appwise.networking.util.HawkUtils
import be.appwise.proxyman.ProxyManNetworkDiscoveryManager
import retrofit2.Response

class NetworkingBuilder(
    val packageName: String = "",
    val clientSecret: String = "",
    val clientId: String = "",
    val appName: String = "",
    val versionName: String = "",
    val versionCode: String = "",
    val apiVersion: String = "",
    val networkingListeners: BaseNetworkingListeners = BaseNetworkingListeners.DEFAULT
) {

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
}
