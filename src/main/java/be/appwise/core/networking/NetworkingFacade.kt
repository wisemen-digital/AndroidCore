package be.appwise.core.networking

import be.appwise.core.networking.model.AccessToken
import okhttp3.Response

interface NetworkingFacade {
    fun logout()
    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
    fun responseCount(responseMethod: Response?): Int
    fun isLoggedIn(): Boolean

    val packageName: String
    val clientSecret: String
    val clientId: String

    class EmptyNetworkingFacade : NetworkingFacade {
        override val packageName: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientId: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientSecret: String
            get() = throw Exception("Initialize Networking in Application class first")
        override fun logout() {
            throw Exception("Unable to logout, Networking hasn't been able to build")
        }

        override fun getAccessToken(): AccessToken? {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun saveAccessToken(accessToken: AccessToken) {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun responseCount(responseMethod: Response?): Int {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun isLoggedIn(): Boolean {
            throw Exception("Initialize Networking in Application class first")
        }
    }
}