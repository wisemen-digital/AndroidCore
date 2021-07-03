package be.appwise.core.networking

import android.content.Context
import be.appwise.core.networking.bagel.BagelNetworkDiscoveryManager
import be.appwise.core.networking.base.BaseNetworkingListeners
import be.appwise.core.networking.model.AccessToken
import be.appwise.core.networking.model.ApiError
import retrofit2.Response

object Networking {
    private var networkingFacade: NetworkingFacade? =
        NetworkingFacade.EmptyNetworkingFacade()

    internal fun build(networkingBuilder: Builder) {
        networkingFacade =
            DefaultNetworkingFacade(networkingBuilder)
    }

    fun getAppName() = networkingFacade!!.appName

    fun getVersionName() = networkingFacade!!.versionName

    fun getVersionCode() = networkingFacade!!.versionCode

    fun getApiVersion() = networkingFacade!!.apiVersion

    fun getPackageName() = networkingFacade!!.packageName

    fun getAccessToken() = networkingFacade!!.getAccessToken()

    fun saveAccessToken(accessToken: AccessToken) = networkingFacade!!.saveAccessToken(accessToken)

    fun getClientIdValue() = networkingFacade!!.clientId

    fun getClientSecretValue() = networkingFacade!!.clientSecret

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
        networkingFacade!!.logout()
    }

    fun parseError(response: Response<*>): ApiError {
        return networkingFacade!!.parseError(response)
    }

    class Builder {
        private var packageName: String = ""
        private var clientSecretValue = ""
        private var clientIdValue = ""
        private var appName = ""
        private var versionName = ""
        private var versionCode = ""
        private var apiVersion = ""
        private var networkingListeners = BaseNetworkingListeners.DEFAULT

        fun setPackageName(packageName: String): Builder {
            this.packageName = packageName
            return this
        }

        internal fun getPackageName(): String {
            return packageName
        }

        fun setClientSecretValue(clientSecretValue: String): Builder {
            this.clientSecretValue = clientSecretValue
            return this
        }

        internal fun getClientSecretValue(): String {
            return clientSecretValue
        }

        fun setClientIdValue(clientIdValue: String): Builder {
            this.clientIdValue = clientIdValue
            return this
        }

        internal fun getClientIdValue(): String {
            return clientIdValue
        }

        fun setAppName(appName: String): Builder {
            this.appName = appName
            return this
        }

        internal fun getAppName(): String {
            return appName
        }

        fun setVersionName(versionName: String): Builder {
            this.versionName = versionName
            return this
        }

        internal fun getVersionName(): String {
            return versionName
        }

        fun setVersionCode(versionCode: String): Builder {
            this.versionCode = versionCode
            return this
        }

        internal fun getVersionCode(): String {
            return versionCode
        }

        fun setApiVersion(apiVersion: String): Builder {
            this.apiVersion = apiVersion
            return this
        }

        internal fun getApiVersion(): String {
            return apiVersion
        }

        fun setNetworkingListeners(customNetworkingListeners: BaseNetworkingListeners): Builder {
            this.networkingListeners = customNetworkingListeners
            return this
        }

        internal fun getNetworkingListeners(): BaseNetworkingListeners {
            return networkingListeners
        }

        fun registerBagelService(context: Context) : Builder{
            BagelNetworkDiscoveryManager.registerService(context)
            return this
        }

        fun build() {
            build(this)
        }
    }
}