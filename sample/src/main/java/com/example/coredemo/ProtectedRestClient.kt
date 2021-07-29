package com.example.coredemo

import be.appwise.networking.base.BaseRestClient
import be.appwise.realm.RealmUtils
import retrofit2.Retrofit

class ProtectedRestClient : BaseRestClient() {
    override val protectedClient: Boolean = false
    override fun getBaseUrl(): String = ""

    override fun createRetrofit(baseUrl: String): Retrofit {
        return super.createRetrofit(baseUrl).newBuilder()
            .addConverterFactory(RealmUtils.getRealmGsonFactory())
            .build()
    }
}