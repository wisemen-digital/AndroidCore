package be.appwise.core.networking

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import be.appwise.core.R
import be.appwise.core.networking.models.AccessToken
import be.appwise.core.networking.models.ApiError
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import id.zelory.compressor.Compressor
import io.realm.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.net.UnknownHostException

class DefaultNetworkingFacade<T>(networkingBuilder: NetworkingBuilder, apiManagerService: Class<T>?) :
    NetworkingFacade {

    //<editor-fold desc="Variables">
    /**
     * These are all variables that are needed to make this class/library work.
     * They have to be in this order as well, else things won't compile and break.
     */
    private val context = networkingBuilder.getContext()
    private val endPoint = networkingBuilder.getEndPoint()
    private val appName = networkingBuilder.getAppName()
    private val versionName = networkingBuilder.getVersionName()
    private val versionCode = networkingBuilder.getVersionCode()
    private val apiVersion = networkingBuilder.getApiVersion()
    private val applicationId = networkingBuilder.getApplicationId()
    private val listener: NetworkingListeners = networkingBuilder.getNetworkingListeners()

    override val clientId = networkingBuilder.getClientIdValue()
    override val clientSecret = networkingBuilder.getClientSecretValue()
    override val packageName = networkingBuilder.getPackageName()

    override val unProtectedRetrofit by lazy {
        getRetro(false)
    }

    override val protectedRetrofit by lazy {
        getRetro(true)
    }

    override val protectedClient by lazy {
        getClient(true)
    }

    override val unProtectedClient by lazy {
        getClient(false)
    }

    //TODO: provide the RestClient by a Factory/Provide pattern
    private val protectedApiManager = protectedRetrofit.create(apiManagerService!!)
    private val unProtectedApiManager = unProtectedRetrofit.create(apiManagerService!!)
    //</editor-fold>

    override fun getContext(): Context {
        return context
    }

    override fun <T> getProtectedApiManager(): T? {
        return protectedApiManager as T
    }

    override fun <T> getUnProtectedApiManager(): T? {
        return unProtectedApiManager as T
    }

    private fun getRetro(protected: Boolean): Retrofit {
        val client = if (protected) {
            protectedClient
        } else {
            unProtectedClient
        }

        return Retrofit.Builder().baseUrl(endPoint).addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(getFactory()).client(client).build()
    }

    private fun getClient(protected: Boolean): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(HeaderInterceptor(appName, versionName, versionCode, apiVersion, applicationId, protected))

        if (protected) {
            client.authenticator(Authenticator(clientId, clientSecret))
        }

        return client.build()
    }

    override suspend fun <T : Any?> doCall(call: Call<T>): T? {
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
            throw Exception(context.getString(R.string.internet_connection_error))
        }
    }

    private fun getGson(): Gson {
        val builder = GsonBuilder()
        builder.setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        })
        return builder.create()
    }

    private fun getFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(getGson())
    }

    override fun responseCount(responseMethod: Response?): Int {
        var response = responseMethod
        var result = 0
        while (response != null) {
            response = response.priorResponse()
            if (response == response?.priorResponse()) {
                result++
            }
        }
        return result
    }

    @SuppressLint("DefaultLocale")
    private fun getMimeType(file: File): String {
        val uri = Uri.fromFile(file)
        val mimeType: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr = context.contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }
        return mimeType ?: ""
    }

    suspend fun getBodyForImage(key: String, file: File): MultipartBody.Part = withContext(Dispatchers.Default) {
        val requestFile = RequestBody.create(MediaType.parse(getMimeType(file)), resizeFile(file))
        MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

    @Throws(IOException::class)
    suspend fun resizeFile(file: File): File = withContext(Dispatchers.IO) {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.path, options)

        val imageHeight = options.outHeight.toDouble()
        val imageWidth = options.outWidth.toDouble()
        //720p is 1280 x 720 px
        val newHeight: Double
        val newWidth: Double
        if (imageHeight > imageWidth) { //portrait-->720p
            val ratio = imageHeight / 720
            newWidth = imageWidth / ratio
            newHeight = 720.0
        } else {
            val ratio = imageWidth / 720
            newWidth = 720.0
            newHeight = imageHeight / ratio
        }

        Compressor(context).setMaxHeight(newHeight.toInt()).setMaxWidth(newWidth.toInt()).setQuality(80)
            .compressToFile(file)
    }

    override fun getAccessToken(): AccessToken? {
        return Hawk.get<AccessToken>(NetworkConstants.HAWK_ACCESS_TOKEN_KEY, null)
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        Hawk.put(NetworkConstants.HAWK_ACCESS_TOKEN_KEY, accessToken)
    }

    override fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    // Implementation of listeners
    private fun parseError(response: retrofit2.Response<*>): ApiError {
        return listener.errorListener(response)
    }

    override fun logout() {
        listener.logout()
    }
}