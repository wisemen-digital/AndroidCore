package com.example.coredemo

import android.app.Application
import be.appwise.core.core.CoreApp
import be.appwise.networking.Networking
import be.appwise.networking.NetworkingConfig
import be.appwise.networking.ProxyManConfig
import be.appwise.networking.base.BaseNetworkingListeners
import be.appwise.networking.model.ApiError
import retrofit2.Response

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

            initializeLogger(getString(R.string.app_name), BuildConfig.DEBUG)
        }
    }

    private fun initNetworking() {
        val networkingConfig = NetworkingConfig(
            appName = getString(R.string.app_name),
            versionCode = BuildConfig.VERSION_CODE.toString(),
            versionName = BuildConfig.VERSION_NAME
        )

        val proxyManConfig = ProxyManConfig(
            enabled = BuildConfig.DEBUG && resources.getBoolean(R.bool.enableProxyman)
        )

        Networking.init(networkingConfig, NetworkingListeners(), proxyManConfig)
    }
}

class NetworkingListeners: BaseNetworkingListeners {
    override fun logout() {
        return super.logout()
    }

    override fun parseError(response: Response<*>): ApiError {
        return super.parseError(response)
    }

    override fun extraLogoutStep() {
        super.extraLogoutStep()
    }
}