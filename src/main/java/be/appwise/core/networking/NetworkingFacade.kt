package be.appwise.core.networking

import be.appwise.core.networking.model.AccessToken

interface NetworkingFacade {
    val appName: String
    val versionName: String
    val versionCode: String
    val apiVersion: String
    val packageName: String
    val clientSecret: String
    val clientId: String

    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
    fun isLoggedIn(): Boolean
    fun logout()
    fun refreshTokenCall(): AccessToken?

    class EmptyNetworkingFacade : NetworkingFacade {
        override val appName: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val versionName: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val versionCode: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val apiVersion: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val packageName: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientId: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientSecret: String
            get() = throw Exception("Initialize Networking in Application class first")

        override fun getAccessToken(): AccessToken? {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun saveAccessToken(accessToken: AccessToken) {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun isLoggedIn(): Boolean {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun logout() {
            throw Exception("Unable to logout, Networking hasn't been able to build")
        }

        override fun refreshTokenCall(): AccessToken? {
            throw Exception("Initialize Networking in Application class first")
        }
    }
}