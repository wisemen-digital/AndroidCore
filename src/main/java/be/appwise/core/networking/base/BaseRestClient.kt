package be.appwise.core.networking.base

import android.util.Log
import be.appwise.core.networking.Authenticator
import be.appwise.core.networking.HeaderInterceptor
import be.appwise.core.networking.Networking
import be.appwise.core.networking.NetworkingUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class BaseRestClient<T> {
    companion object {
        const val TAG = "BaseRestClient"
    }

    protected abstract val apiService: Class<T>

    protected abstract val protectedClient: Boolean

    protected abstract fun getBaseUrl(): String

    protected open val appName = Networking.getAppName()
    protected open val versionName = Networking.getVersionName()
    protected open val versionCode = Networking.getVersionCode()
    protected open val apiVersion = Networking.getApiVersion()
    protected open val packageName = Networking.getPackageName()

    val getService: T by lazy {
        Log.d(TAG, "getService()")
        getRetrofit.create(apiService)
    }

    val getHttpClient: OkHttpClient by lazy {
        Log.d(TAG, "getHttpClient")
        createHttpClient()
    }

    private val getRetrofit: Retrofit by lazy {
        Log.d(TAG, "getRetrofit")
        createRetrofit()
    }

    protected open fun createHttpClient(): OkHttpClient {
        Log.d(TAG, "createHttpClient")
        val builder = OkHttpClient.Builder()
            .addInterceptor(getHttpLogging())
            .addInterceptor(
                HeaderInterceptor(
                    appName,
                    versionName,
                    versionCode,
                    apiVersion,
                    packageName,
                    protectedClient
                )
            )

        if (protectedClient) {
            builder.authenticator(Authenticator)
        }

        return builder.build()
    }

    protected open fun createRetrofit(): Retrofit {
        Log.d(TAG, "createRetrofit")
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(getFactory())
            .client(getHttpClient)
            .build()
    }

    protected fun getFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(NetworkingUtil.getGson())
    }

    protected fun getHttpLogging(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}