package com.example.coredemo

import android.os.Bundle
import be.appwise.core.ui.base.BaseActivity
import be.appwise.networking.base.BaseRestClient
import com.google.gson.Gson

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

object RestClient : BaseRestClient() {
    override val protectedClient: Boolean
        get() = true

    override fun getBaseUrl(): String {
        return ""
    }

    override fun getGson(): Gson {
        return super.getGson().newBuilder()
            .setPrettyPrinting()
            .create()
    }
}