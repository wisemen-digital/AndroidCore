package com.shahar91.core.networking.newNetwork

import io.reactivex.Observable
import okhttp3.Response
import retrofit2.Call

interface NetworkingFacade {
    fun logout()
    fun <T : Any?> doCallRx(call: Call<T>): Observable<T>
    suspend fun <T : Any?> doCallCr(call: Call<T>): T
    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
    fun responseCount(responseMethod: Response?): Int
    fun <T> getProtectedApiManager(): T?
    fun <T> getUnProtectedApiManager(): T?
    fun isLoggedIn(): Boolean

    class EmptyNetworkingFacade() : NetworkingFacade {
        override fun logout() {
            //TODO: throw error???
        }

        override fun <T> doCallRx(call: Call<T>): Observable<T> {
            return Observable.empty()
        }

        override suspend fun <T> doCallCr(call: Call<T>): T {
            throw Exception("Something went wrong")
        }

        override fun getAccessToken(): AccessToken? {
            return null
        }

        override fun saveAccessToken(accessToken: AccessToken) {
            throw Exception("Something went wrong")
        }

        override fun responseCount(responseMethod: Response?): Int {
            throw Exception("Something went wrong")
        }

        override fun <T> getProtectedApiManager(): T? {
            return null
        }

        override fun <T> getUnProtectedApiManager(): T? {
            return null
        }

        override fun isLoggedIn(): Boolean {
            return false
        }
    }

}

