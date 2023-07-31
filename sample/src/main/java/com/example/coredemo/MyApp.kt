package com.example.coredemo

import android.app.Application
import androidx.multidex.BuildConfig
import be.appwise.core.core.CoreApp
import be.appwise.networking.Networking

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
        Networking.init {
            setAppName(getString(R.string.app_name))
            setVersionCode(BuildConfig.VERSION_CODE.toString())
            setVersionName(BuildConfig.VERSION_NAME)
            apply {
                if (BuildConfig.DEBUG && resources.getBoolean(R.bool.enableProxyman)) {
                    registerProxymanService(this@MyApp)
                }
            }
        }
    }
}