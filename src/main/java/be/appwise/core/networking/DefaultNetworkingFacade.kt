package be.appwise.core.networking

import be.appwise.core.networking.base.BaseNetworkingListeners
import be.appwise.core.networking.model.AccessToken
import be.appwise.core.util.HawkUtils
import com.orhanobut.hawk.Hawk
import okhttp3.Response

class DefaultNetworkingFacade(networkingBuilder: Networking.NetworkBuilder) :
    NetworkingFacade {

    //<editor-fold desc="Variables">
    /**
     * These are all variables that are needed to make this class/library work.
     * They have to be in this order as well, else things won't compile and break.
     */
    override val appName = networkingBuilder.getAppName()
    override val versionName = networkingBuilder.getVersionName()
    override val versionCode = networkingBuilder.getVersionCode()
    override val apiVersion = networkingBuilder.getApiVersion()
    override val applicationId = networkingBuilder.getApplicationId()
    private val listener: BaseNetworkingListeners = networkingBuilder.getNetworkingListeners()

    override val clientId = networkingBuilder.getClientIdValue()
    override val clientSecret = networkingBuilder.getClientSecretValue()
    //</editor-fold>

    override fun responseCount(responseMethod: Response?): Int {
        var response = responseMethod
        var result = 0
        while (response != null) {
            response = response.priorResponse()
            if (response == response?.priorResponse()) {
                result++
            }
        }
        return result
    }

    override fun getAccessToken() = HawkUtils.hawkAccessToken

    override fun saveAccessToken(accessToken: AccessToken) {
        HawkUtils.hawkAccessToken = accessToken
    }

    override fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    override fun logout() {
        listener.logout()
    }
}
