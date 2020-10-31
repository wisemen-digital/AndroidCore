package be.appwise.core.networking

import be.appwise.core.networking.base.BaseNetworkingListeners
import be.appwise.core.networking.model.AccessToken

object Networking {
    private var networkingFacade: NetworkingFacade? =
        NetworkingFacade.EmptyNetworkingFacade()

    internal fun build(networkingBuilder: NetworkBuilder) {
        networkingFacade =
            DefaultNetworkingFacade(networkingBuilder)
    }

    fun getPackageName() {
        networkingFacade!!.packageName
    }

    fun getAccessToken(): AccessToken? {
        return networkingFacade!!.getAccessToken()
    }

    fun saveAccessToken(accessToken: AccessToken) {
        networkingFacade!!.saveAccessToken(accessToken)
    }

    /**
     * This logout function can be used to cleanup any resources the app is using.
     * i.e. remove all entries from Hawk, delete all data from Realm, ...
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

    class NetworkBuilder {
        private var endPoint: String = ""
        private var packageName: String = ""
        private var clientSecretValue = ""
        private var clientIdValue = ""
        private var appName = ""
        private var versionName = ""
        private var versionCode = ""
        private var apiVersion = ""
        private var applicationId = ""
        private var networkingListeners = BaseNetworkingListeners.DEFAULT

        fun setEndPoint(endPoint: String): NetworkBuilder {
            this.endPoint = endPoint
            return this
        }

        internal fun getEndPoint(): String {
            return endPoint
        }

        fun setPackageName(packageName: String): NetworkBuilder {
            this.packageName = packageName
            return this
        }

        internal fun getPackageName(): String {
            return packageName
        }

        fun setClientSecretValue(clientSecretValue: String): NetworkBuilder {
            this.clientSecretValue = clientSecretValue
            return this
        }

        internal fun getClientSecretValue(): String {
            return clientSecretValue
        }

        fun setClientIdValue(clientIdValue: String): NetworkBuilder {
            this.clientIdValue = clientIdValue
            return this
        }

        internal fun getClientIdValue(): String {
            return clientIdValue
        }

        fun setAppName(appName: String): NetworkBuilder {
            this.appName = appName
            return this
        }

        internal fun getAppName(): String {
            return appName
        }

        fun setVersionName(versionName: String): NetworkBuilder {
            this.versionName = versionName
            return this
        }

        internal fun getVersionName(): String {
            return versionName
        }

        fun setVersionCode(versionCode: String): NetworkBuilder {
            this.versionCode = versionCode
            return this
        }

        internal fun getVersionCode(): String {
            return versionCode
        }

        fun setApiVersion(apiVersion: String): NetworkBuilder {
            this.apiVersion = apiVersion
            return this
        }

        internal fun getApiVersion(): String {
            return apiVersion
        }

        fun setApplicationId(applicationId: String): NetworkBuilder {
            this.applicationId = applicationId
            return this
        }

        internal fun getApplicationId(): String {
            return applicationId
        }

        fun setNetworkingListeners(customNetworkingListeners: BaseNetworkingListeners): NetworkBuilder {
            this.networkingListeners = customNetworkingListeners
            return this
        }

        internal fun getNetworkingListeners(): BaseNetworkingListeners {
            return networkingListeners
        }

        fun build() {
            build(this)
        }
    }
}