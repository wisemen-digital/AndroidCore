package be.appwise.core.networking

import android.app.PendingIntent
import android.content.Intent
import be.appwise.core.R
import be.appwise.core.networking.models.ApiError
import com.orhanobut.hawk.Hawk
import io.realm.Realm
import org.json.JSONException
import retrofit2.Response

/**
 * Implement this interface and add it to the initial builder pattern to override the functions.
 * You can choose which function to implement and which may still use the default implementation.
 */
interface NetworkingListeners {
    companion object {
        val DEFAULT = object : NetworkingListeners {}
    }

    fun errorListener(response: Response<*>): ApiError {
        var error = ApiError()

        when (response.code()) {
            500 -> error.message = Networking.getContext().getString(R.string.api_error_fallback)
            404 -> error.message = Networking.getContext()
                .resources.getString(R.string.internet_connection_error)
            401 -> error.message = Networking.getContext()
                .resources.getString(R.string.api_error_authentication)
            422 -> try {
                val hashMapConverter = Networking.getProtectedRetrofit()
                    .responseBodyConverter<HashMap<*, *>>(HashMap::class.java, arrayOfNulls<Annotation>(0))

                var message = ""
                val hashMapResponseBody = response.errorBody()
                val hashMap = hashMapConverter.convert(hashMapResponseBody!!)
                if (hashMap?.containsKey("errors") == true) {
                    val errorMaps = jsonToMap(hashMap["errors"].toString())
                    for (key in errorMaps.keys) {
                        message += errorMaps[key]
                    }
                    error.message = message.replace("[", "").replace("]", "")
                } else {
                    for (entry in hashMap!!.keys) {
                        val value = hashMap[entry].toString()
                        message = if (value.contains("verplicht") || value.contains("required")) {
                            "$value $entry"
                        } else value
                    }
                    error.message = message
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> {
                val converter = Networking.getProtectedRetrofit()
                    .responseBodyConverter<ApiError>(ApiError::class.java, arrayOfNulls<Annotation>(0))

                val responseBody = response.errorBody()
                error = try {
                    converter.convert(responseBody!!)!!
                } catch (e: Exception) {
                    ApiError()
                }
            }
        }

        return error
    }

    @Throws(JSONException::class)
    fun jsonToMap(t: String): HashMap<String, String> {
        val myMap = HashMap<String, String>()
        val pairs = t.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in pairs.indices) {
            val pair = pairs[i]
            val keyValue = pair.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            myMap[keyValue[0]] = keyValue[1]
        }

        return myMap
    }

    fun logout() {
        val realm = Realm.getDefaultInstance()

        val errorActivity = Intent("${Networking.getPackageName()}.logout")
        errorActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(Networking.getContext(), 22, errorActivity, 0)

        Networking.getUnProtectedClient().dispatcher().cancelAll()

        try {
            if (Hawk.isBuilt()) {
                Hawk.deleteAll()
            }

            realm.executeTransaction { it.deleteAll() }
            pendingIntent.send()

            //OneSignal.deleteTag(Constants.ONESIGNAL_USER_ID)
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }
    }
}