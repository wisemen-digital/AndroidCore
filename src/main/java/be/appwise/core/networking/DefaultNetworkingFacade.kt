package be.appwise.core.networking

import be.appwise.core.networking.base.BaseNetworkingListeners
import be.appwise.core.networking.model.AccessToken
import com.orhanobut.hawk.Hawk
import okhttp3.Response

class DefaultNetworkingFacade(networkingBuilder: Networking.NetworkBuilder) :
    NetworkingFacade {

    //<editor-fold desc="Variables">
    /**
     * These are all variables that are needed to make this class/library work.
     * They have to be in this order as well, else things won't compile and break.
     */
    private val endPoint = networkingBuilder.getEndPoint()
    private val appName = networkingBuilder.getAppName()
    private val versionName = networkingBuilder.getVersionName()
    private val versionCode = networkingBuilder.getVersionCode()
    private val apiVersion = networkingBuilder.getApiVersion()
    private val applicationId = networkingBuilder.getApplicationId()
    private val listener: BaseNetworkingListeners = networkingBuilder.getNetworkingListeners()

    override val clientId = networkingBuilder.getClientIdValue()
    override val clientSecret = networkingBuilder.getClientSecretValue()
    override val packageName = networkingBuilder.getPackageName()
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

    override fun getAccessToken(): AccessToken? {
        return Hawk.get<AccessToken>(NetworkConstants.HAWK_ACCESS_TOKEN_KEY, null)
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        Hawk.put(NetworkConstants.HAWK_ACCESS_TOKEN_KEY, accessToken)
    }

    override fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    override fun logout() {
        listener.logout()
    }
}
