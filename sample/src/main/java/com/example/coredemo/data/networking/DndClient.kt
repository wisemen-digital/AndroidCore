package com.example.coredemo.data.networking

import be.appwise.networking.base.BaseRestClient
import com.example.coredemo.data.networking.services.SpellService

object DndClient : BaseRestClient() {

    override val protectedClient = false
    override fun getBaseUrl() = "https://api.open5e.com/"
    override fun enableProxyManInterceptor() = true

    val getSpellsService: SpellService by lazy {
        getRetrofit.create(SpellService::class.java)
    }
}