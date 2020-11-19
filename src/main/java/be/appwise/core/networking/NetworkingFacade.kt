package be.appwise.core.networking

import android.content.Context
import be.appwise.core.networking.models.AccessToken
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit

interface NetworkingFacade {
    fun logout()
    suspend fun <T : Any?> doCall(call: Call<T>): T?
    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
    fun responseCount(responseMethod: Response?): Int
    fun <T> getProtectedApiManager(): T?
    fun <T> getUnProtectedApiManager(): T?
    fun isLoggedIn(): Boolean

    fun getContext(): Context

    val packageName: String
    val clientSecret: String
    val clientId: String
    val protectedRetrofit: Retrofit
    val unProtectedRetrofit: Retrofit
    val protectedClient: OkHttpClient
    val unProtectedClient: OkHttpClient

    class EmptyNetworkingFacade : NetworkingFacade {
        override val protectedClient: OkHttpClient
            get() = throw Exception("Initialize Networking in Application class first")
        override val unProtectedClient: OkHttpClient
            get() = throw Exception("Initialize Networking in Application class first")
        override val packageName: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientId: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val clientSecret: String
            get() = throw Exception("Initialize Networking in Application class first")
        override val protectedRetrofit: Retrofit
            get() = throw Exception("Initialize Networking in Application class first")
        override val unProtectedRetrofit: Retrofit
            get() = throw Exception("Initialize Networking in Application class first")

        override fun logout() {
            throw Exception("Unable to logout, Networking hasn't been able to build")
        }

        override suspend fun <T> doCall(call: Call<T>): T? {
            throw Exception("Can't do any calls, initialize Networking in Application class first")
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

        override fun <T> getProtectedApiManager(): T? {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun <T> getUnProtectedApiManager(): T? {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun isLoggedIn(): Boolean {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun getContext(): Context {
            throw Exception("Initialize Networking in Application class first")
        }
    }
}