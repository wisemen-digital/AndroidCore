package com.example.coredemo

import android.app.Application
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
        CoreApp.init(this)
            .apply {
                // Only show an Error Activity on a DEBUG build
                if (BuildConfig.DEBUG) {
                    initializeErrorActivity(true)
                }
            }
            .initializeLogger(getString(R.string.app_name), BuildConfig.DEBUG)
            .build()
    }

    private fun initNetworking() {
        Networking.Builder(this)
            .registerProxymanService(this)
            .setPackageName(packageName)
            .setAppName(getString(R.string.app_name))
            .setVersionCode(BuildConfig.VERSION_CODE.toString())
            .setVersionName(BuildConfig.VERSION_NAME)
            .setApiVersion("1")
            .build()
    }
}