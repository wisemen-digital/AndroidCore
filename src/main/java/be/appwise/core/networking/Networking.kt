package be.appwise.core.networking

import android.content.Context
import be.appwise.core.networking.models.AccessToken
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit

object Networking {
    private var networkingFacade: NetworkingFacade? =
        NetworkingFacade.EmptyNetworkingFacade()

    internal fun <T> build(
        networkingBuilder: NetworkingBuilder,
        apiManagerService: Class<T>?
    ) {
        networkingFacade =
            DefaultNetworkingFacade(networkingBuilder, apiManagerService)
    }

    internal fun getContext(): Context {
        return networkingFacade!!.getContext()
    }

    /**
     * Use this function to get the result of a network call.
     * In case of any errors this will map the error using the ApiError object.
     *
     * This is a suspended function and is only useful for Coroutines.
     */
    suspend fun <T : Any?> doCall(call: Call<T>): T? {
        return networkingFacade!!.doCall(call)
    }

    fun getAccessToken(): AccessToken? {
        return networkingFacade!!.getAccessToken()
    }

    fun saveAccessToken(accessToken: AccessToken) {
        networkingFacade!!.saveAccessToken(accessToken)
    }

    internal fun responseCount(responseMethod: Response?): Int {
        return networkingFacade!!.responseCount(responseMethod)
    }

    fun <T> getProtectedApiManager(): T? {
        return networkingFacade!!.getProtectedApiManager()
    }

    fun <T> getUnProtectedApiManager(): T? {
        return networkingFacade!!.getUnProtectedApiManager()
    }

    /**
     * Check if the use is logged in
     *
     * Basically, if there is an AccessToken saved, the user is logged in
     */
    @JvmStatic
    fun isLoggedIn(): Boolean {
        return networkingFacade!!.isLoggedIn()
    }

    fun getProtectedRetrofit(): Retrofit {
        return networkingFacade!!.protectedRetrofit
    }

    fun getUnProtectedRetrofit(): Retrofit {
        return networkingFacade!!.unProtectedRetrofit
    }

    fun getProtectedClient(): OkHttpClient {
        return networkingFacade!!.protectedClient
    }

    fun getUnProtectedClient(): OkHttpClient {
        return networkingFacade!!.unProtectedClient
    }

    fun getPackageName(): String {
        return networkingFacade!!.packageName
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
}