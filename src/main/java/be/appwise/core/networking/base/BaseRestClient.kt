package be.appwise.core.networking.base

import android.util.Log

abstract class BaseRestClient<T>: BaseSimpleRestClient() {

    protected abstract val apiService: Class<T>

    /**
     * Get the service attached to this retrofit object to do the network requests
     */
    val getService: T by lazy {
        Log.d(TAG, "getService()")
        getRetrofit.create(apiService)
    }
}