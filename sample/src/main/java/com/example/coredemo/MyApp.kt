package com.example.coredemo

import android.app.Application
import androidx.multidex.BuildConfig
import be.appwise.core.core.CoreApp
import be.appwise.networking.Networking
import be.appwise.networking.NetworkingBuilder

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
        Networking.init(
            NetworkingBuilder(
                appName = getString(R.string.app_name),
                versionCode = BuildConfig.VERSION_CODE.toString(),
                versionName = BuildConfig.VERSION_NAME
            ).apply {
                if (BuildConfig.DEBUG && resources.getBoolean(R.bool.enableProxyman)) {
                    registerProxymanService(this@MyApp)
                }
            }
        )
    }
}