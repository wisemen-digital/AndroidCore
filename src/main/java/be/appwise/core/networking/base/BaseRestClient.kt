package be.appwise.core.networking.base

import android.util.Log
import be.appwise.core.R
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import be.appwise.core.networking.NetworkingUtil
import be.appwise.core.networking.interceptors.Authenticator
import be.appwise.core.networking.interceptors.HeaderInterceptor
import be.appwise.core.networking.model.ApiError
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.UnknownHostException

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

    /**
     * Get the service attached to this retrofit object to do the network requests
     */
    val getService: T by lazy {
        Log.d(TAG, "getService()")
        getRetrofit.create(apiService)
    }

    /**
     * Get the OkHttpClient for this RestClient along with all the interceptors, headers, and more
     */
    val getHttpClient: OkHttpClient by lazy {
        Log.d(TAG, "getHttpClient")
        createHttpClient()
    }

    /**
     * Get the Retrofit object for this RestClient along with all Converters, BaseUrl, OkHttpClient, and more
     */
    val getRetrofit: Retrofit by lazy {
        Log.d(TAG, "getRetrofit")
        createRetrofit()
    }

    protected open fun createHttpClient(): OkHttpClient {
        Log.d(TAG, "createHttpClient")
        val builder = OkHttpClient.Builder()

        getInterceptors().forEach {
            builder.addInterceptor(it)
        }

        if (protectedClient) {
            builder.authenticator(Authenticator)
        }

        return builder.build()
    }

    /**
     * Create a basis Retrofit object with a couple of ConverterFactories added to it
     * as well as the baseUrl and the okHttpClient.
     *
     * If a project specific object should be needed, this function can be overridden to suit your needs.
     */
    protected open fun createRetrofit(): Retrofit {
        Log.d(TAG, "createRetrofit")
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(getGsonFactory())
            .client(getHttpClient)
            .build()
    }

    //<editor-fold desc="Interceptors">
    /**
     * Get the default HttpLoggingInterceptor that is being used in almost every project
     */
    protected fun getHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * Get the default HeaderInterceptor that is being used in almost every project
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
     * Get a default list of interceptors to be added to the restClient.
     *
     * In case a project specific order is needed this function can be
     * overridden and changed as needed (reordering, omitting or adding)
     */
    open fun getInterceptors(): List<Interceptor> {
        return listOf(
            getHttpLoggingInterceptor(), getHeaderInterceptor()
        )
    }
    //</editor-fold>

    /**
     * Get the Gson Factory to handle all cases of type conversions from the responses
     */
    protected fun getGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(NetworkingUtil.getGson())
    }

    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    suspend fun <T : Any?> doCall(call: Call<T>): T? {
        return try {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    throw Exception(parseError(response).message)
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception(CoreApp.getContext().getString(R.string.internet_connection_error))
        }
    }

    //<editor-fold desc="ErrorHandling">
    /**
     * This function will be used to handle any errors that come from the network responses
     * Whenever you need to customize your error string, just add a string resource to your
     * projects resource file.
     *
     * In case you need to customize the entire error handling you can override this function
     * in your implementation of the RestClient
     */
    open fun parseError(response: Response<*>) = ApiError().apply {
        message = when (response.code()) {
            500 -> CoreApp.getContext().getString(R.string.internal_server_error)
            404 -> CoreApp.getContext().getString(R.string.network_error)
            /*400 -> CoreApp.getContext().getString(R.string.login_error)*/
            401 -> CoreApp.getContext().getString(R.string.login_error)
            else -> {
                //TODO: a new retrofit has been created in order to have no build errors... to test still!!!
                val hashMapConverter =
                    Retrofit.Builder().build().responseBodyConverter<JsonElement>(
                        JsonElement::class.java, arrayOfNulls<Annotation>(0)
                    )
                when (val errorJson = hashMapConverter.convert(response.errorBody()!!)) {
                    is JsonArray -> manageJsonArrayFormat(errorJson)
                    is JsonObject -> manageJsonObjectFormat(errorJson)
                    else -> "Something went wrong with parsing error"
                }
            }
        }
    }

    private fun manageJsonObjectFormat(hashMap: JsonObject): String {
        if (hashMap.has("errors")) {
            val errors = hashMap.get("errors").asJsonObject
            return errors.entrySet().joinToString(separator = "") {
                if (it.value.isJsonArray) it.value.asJsonArray.first().asString else it.value.asString
            }
        } else {
            var message = ""
            if (hashMap.has("message")) {
                message = hashMap.get("message").asString
            } else {
                for (entry in hashMap.entrySet()) {
                    val value = entry.value.asString
                    val key = entry.key
                    if (value.contains("verplicht") || value.contains("required")) {
                        message += "$value $key"
                    } else
                    // Take this route when the message is detailed enough
                        message = value
                }
            }
            return message
        }
    }

    private fun manageJsonArrayFormat(jsonArray: JsonArray): String {
        return jsonArray.first()?.asJsonObject?.get("message")?.asString
            ?: "" /*.joinToString{ it.asJsonObject.get("message").asString}*/
    }
    //</editor-fold>
}