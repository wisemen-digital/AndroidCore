package com.example.coredemo

import android.app.Application
import be.appwise.core.core.CoreApp
import be.appwise.networking.Networking
import be.appwise.networking.NetworkingConfig
import be.appwise.networking.base.BaseNetworkingListeners

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initCore()
        initNetworking()
    }

    private fun initCore() {
        CoreApp.init {
            if (BuildConfig.DEBUG) {
                initializeErrorActivity(true)
            }
        }
    }

    private fun initNetworking() {
        val networkingConfig = NetworkingConfig(
            appName = getString(R.string.app_name),
            versionCode = BuildConfig.VERSION_CODE.toString(),
            versionName = BuildConfig.VERSION_NAME
        )

        Networking.init(networkingConfig, NetworkingListeners())
    }
}

class NetworkingListeners: BaseNetworkingListeners