package be.appwise.core.networking

import android.content.Context
import io.reactivex.Observable
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit

interface NetworkingFacade {
    fun logout()
    fun <T : Any?> doCallRx(call: Call<T>): Observable<T>
    suspend fun <T : Any?> doCallCr(call: Call<T>): T
    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
    fun responseCount(responseMethod: Response?): Int
    fun <T> getProtectedApiManager(): T?
    fun <T> getUnProtectedApiManager(): T?
    fun getProtectedRetrofit(): Retrofit
    fun getUnProtectedRetrofit(): Retrofit
    fun isLoggedIn(): Boolean
    fun getContext(): Context

    class EmptyNetworkingFacade : NetworkingFacade {
        override fun getProtectedRetrofit(): Retrofit {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun getUnProtectedRetrofit(): Retrofit {
            throw Exception("Initialize Networking in Application class first")
        }

        override fun logout() {
            throw Exception("Unable to logout, Networking hasn't been able to build")
        }

        override fun <T> doCallRx(call: Call<T>): Observable<T> {
            return Observable.empty()
        }

        override suspend fun <T> doCallCr(call: Call<T>): T {
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