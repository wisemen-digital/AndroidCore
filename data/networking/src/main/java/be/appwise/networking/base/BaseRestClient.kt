package be.appwise.networking.base

import android.os.Build
import android.provider.Settings
import android.util.Log
import be.appwise.networking.NetworkConstants
import be.appwise.networking.Networking
import be.appwise.networking.bagel.BagelInterceptor
import be.appwise.networking.interceptors.Authenticator
import be.appwise.networking.interceptors.HeaderInterceptor
import be.appwise.networking.model.AccessToken
import be.appwise.networking.proxyman.ProxyManInterceptor
import be.appwise.networking.responseAdapter.NetworkResponseAdapterFactory
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class BaseRestClient {
    protected open val TAG: String = BaseRestClient::class.java.simpleName

    protected abstract val protectedClient: Boolean

    protected abstract fun getBaseUrl(): String

    protected open val appName = Networking.getAppName()
    protected open val versionName = Networking.getVersionName()
    protected open val versionCode = Networking.getVersionCode()
    protected open val apiVersion = Networking.getApiVersion()
    protected open val packageName = Networking.getPackageName()
    open val clientId = Networking.getClientIdValue()
    open val clientSecret = Networking.getClientSecretValue()

    /**
     * Get the OkHttpClient for this RestClient along with all the interceptors, headers, and more
     */
    val getHttpClient: OkHttpClient by lazy {
        Log.d(TAG, "getHttpClient")
        createHttpClient()
    }

    /**
     * Get the standard Retrofit object for this RestClient along with all Converters, BaseUrl, OkHttpClient, and more
     *
     * This Retrofit object will be created with the [BaseRestClient.getBaseUrl]
     */
    val getRetrofit: Retrofit by lazy {
        Log.d(TAG, "getRetrofit")
        createRetrofit()
    }

    /**
     * Create a default HttpClient with a list of Interceptors added to it as well as an Authenticator.
     * - The list of Interceptors can be updated by overriding [BaseRestClient.getInterceptors].
     * - The Authenticator is only active when the [BaseRestClient.protectedClient] flag is true.
     * - When the flag [BaseRestClient.enableBagelInterceptor] is set to true,
     *      the [BagelInterceptor] will be added and all calls (request and responses) can be found in Bagel
     *
     * In any case, this function can be overridden to add something specific to the whole configuration, or simply to override everything.
     * If you want to add something to it, you can do so by using the [OkHttpClient.newBuilder].
     *
     * As an example:
     * ```
     * override fun createHttpClient(): OkHttpClient {
     *     return super.createHttpClient()
     *         .newBuilder()
     *         .connectTimeout(1, TimeUnit.MINUTES)
     *         .build()
     * }
     * ```
     */
    protected open fun createHttpClient(): OkHttpClient {
        Log.d(TAG, "createHttpClient")
        val builder = OkHttpClient.Builder()

        getInterceptors().forEach {
            builder.addInterceptor(it)
        }

        if (protectedClient) {
            builder.authenticator(Authenticator { onRefreshToken(it) })
        }
        //add it behind all the rest so we can send all the response/request data
        if (enableBagelInterceptor())
            builder.addInterceptor(getBagelInterceptor())

        //add it behind all the rest so we can send all the response/request data
        if (enableProxyManInterceptor())
            builder.addInterceptor(ProxyManInterceptor())

        return builder.build()
    }

    /**
     * In case [protectedClient] is set to true this function should be overridden without using the 'super()'
     * Using the 'super()' will still result in the exception being thrown!
     */
    protected open fun onRefreshToken(refreshToken: String): AccessToken? {
        throw Exception("RefreshToken should be overridden in order for this to work, do not call 'super()'.")
    }

    /**
     * Create a basis Retrofit object with a couple of ConverterFactories added to it
     * as well as the baseUrl and the okHttpClient.
     *
     * If a project specific object should be needed, this function can be overridden to suit your needs.
     *
     * As an example:
     * ```
     * override fun createRetrofit(): Retrofit {
     *     return super.createRetrofit().newBuilder()
     *         .validateEagerly(true)
     *         .build()
     * }
     *
     * ```
     *
     * @param baseUrl add a specific url when creating a new object. By default this will be empty,
     *  but in case you want to create a specific service you can add a different url, if needed.
     * @return A new retrofit object with a baseUrl added to it, a couple of ConverterFactories and the okHttpClient
     */
    protected open fun createRetrofit(baseUrl: String = ""): Retrofit {
        Log.d(TAG, "createRetrofit")

        val urlToUse = if (baseUrl.isNotBlank()) {
            baseUrl
        } else {
            getBaseUrl()
        }

        val builder = Retrofit.Builder()
            .baseUrl(urlToUse)
            .client(getHttpClient)

        getConverterFactories().forEach {
            builder.addConverterFactory(it)
        }

        builder.addCallAdapterFactory(NetworkResponseAdapterFactory())

        return builder.build()
    }

    /**
     * Get a default list of [Converter.Factory] to be added to the restClient.
     *
     * In case a project specific order is needed this function can be
     * overridden and changed as needed (reordering, omitting or adding)
     *
     * ```kotlin
     *     override fun getConverterFactories(): MutableList<Converter.Factory> {
     *         return super.getConverterFactories().also {
     *             it.add(1, getNewConverterFactory())
     *         }
     *     }
     * ```
     */
    protected open fun getConverterFactories(): MutableList<Converter.Factory> {
        return mutableListOf(ScalarsConverterFactory.create(), getGsonFactory())
    }

    //<editor-fold desc="Interceptors">
    /**
     * Get the default HttpLoggingInterceptor that is being used in almost every project
     * In case Bagel is enabled for this RestClient, the logging for requests and responses will be at a bare minimum.
     *
     * Whenever you need to have project specific level or only want the logging enabled on DEBUG you can override the function.
     */
    protected open fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (enableBagelInterceptor()) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
    }

    /**
     * Get the default [HeaderInterceptor] that is being used in almost every project
     */
    protected fun getHeaderInterceptor() = HeaderInterceptor(
        appName,
        versionName,
        versionCode,
        apiVersion,
        packageName,
        protectedClient
    )

    /**
     * Get the default [BagelInterceptor].
     */
    protected fun getBagelInterceptor(): BagelInterceptor {
        val deviceName = Settings.Secure.getString(Networking.getContext().contentResolver, NetworkConstants.BAGEL_INTERCEPTOR_DEVICE_BLUETOOTH_NAME)
            ?: Settings.Global.getString(Networking.getContext().contentResolver, NetworkConstants.BAGEL_INTERCEPTOR_DEVICE_NAME)
        return BagelInterceptor(
            packageName,
            Settings.Secure.getString(Networking.getContext().contentResolver, Settings.Secure.ANDROID_ID),
            deviceName,
            Build.MANUFACTURER + "," + Build.MODEL + "; Android/" + Build.VERSION.SDK_INT
        )
    }

    /**
     * Get a default list of interceptors to be added to the restClient.
     *
     * In case a project specific order is needed this function can be
     * overridden and changed as needed (reordering, omitting or adding)
     */
    protected open fun getInterceptors(): MutableList<Interceptor> {
        return mutableListOf(
            getHttpLoggingInterceptor(), getHeaderInterceptor()
        )
    }

    /**
     * Allows you to enable the Bagel interceptor for an instance of the [BaseRestClient]
     *
     * It will be added after all other interceptors so headers and other request/response data
     * will be up to date when shown in Bagel
     *
     * Added it here so you can choose for each instance of a [BaseRestClient] if you wish to use it or not.
     *
     * Can be moved to [Networking] so you can enable/disable it for all clients.
     * Maybe also limit it to DEBUG builds in future
     */
    protected open fun enableBagelInterceptor() = false

    /**
     * Allows you to enable the ProxyMan interceptor for an instance of the [BaseRestClient]
     *
     * It will be added after all other interceptors so headers and other request/response data
     * will be up to date when shown in ProxyMan
     *
     * Added it here so you can choose for each instance of a [BaseRestClient] if you wish to use it or not.
     *
     * Can be moved to [Networking] so you can enable/disable it for all clients.
     * Maybe also limit it to DEBUG builds in future
     */
    protected open fun enableProxyManInterceptor() = false
    //</editor-fold>

    /**
     * Get the Gson Factory to handle all cases of type conversions from the responses
     */
    protected open fun getGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(getGson())
    }

    /**
     * Create a Gson instance that can be used within the RestClient.
     * This can easily be overridden by using the 'newBuilder' pattern
     *
     * As an example:
     * ```
     * override fun getGson(): Gson {
     *     return super.getGson().newBuilder()
     *         .setPrettyPrinting()
     *         .create()
     * }
     *
     * ```
     */
    protected open fun getGson(): Gson {
        return Gson()
    }

    /**
     * Can be used to get the service in a more generic way.
     */
    inline fun <reified T> getService(): T = lazy { getRetrofit.create(T::class.java) }.value

}