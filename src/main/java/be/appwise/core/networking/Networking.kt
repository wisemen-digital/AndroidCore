package be.appwise.core.networking

import android.content.Context
import io.reactivex.Observable
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit

object Networking {
    private var networkingFacade: NetworkingFacade? =
        NetworkingFacade.EmptyNetworkingFacade()

    fun init(context: Context): NetworkingBuilder {
        networkingFacade = null
        return NetworkingBuilder(context)
    }

    internal fun <T> build(
        networkingBuilder: NetworkingBuilder,
        apiManagerService: Class<T>?
    ) {
        networkingFacade =
            DefaultNetworkingFacade(networkingBuilder, apiManagerService)
    }

    fun <T> doCallRx(call: Call<T>): Observable<T> {
        return networkingFacade!!.doCallRx(call)
    }

    suspend fun <T : Any?> doCallCr(call: Call<T>): T {
        return networkingFacade!!.doCallCr(call)
    }

    fun logout() {
        networkingFacade!!.logout()
    }

    fun getAccessToken(): AccessToken? {
        return networkingFacade!!.getAccessToken()
    }

    fun saveAccessToken(accessToken: AccessToken) {
        networkingFacade!!.saveAccessToken(accessToken)
    }

    fun responseCount(responseMethod: Response?): Int {
        return networkingFacade!!.responseCount(responseMethod)
    }

    fun <T> getProtectedApiManager(): T? {
        return networkingFacade!!.getProtectedApiManager()
    }

    fun <T> getUnProtectedApiManager(): T? {
        return networkingFacade!!.getUnProtectedApiManager()
    }

    fun isLoggedIn(): Boolean {
        return networkingFacade!!.isLoggedIn()
    }

    fun getProtectedRetrofit(): Retrofit{
        return networkingFacade!!.getProtectedRetrofit()
    }

    fun getUnProtectedRetrofit():Retrofit{
        return networkingFacade!!.getUnProtectedRetrofit()
    }

    fun getContext(): Context{
        return networkingFacade!!.getContext()
    }
}