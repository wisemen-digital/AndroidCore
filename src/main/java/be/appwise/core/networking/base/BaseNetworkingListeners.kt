package be.appwise.core.networking.base

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.room.RoomDatabase
import be.appwise.core.R
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import be.appwise.core.networking.model.ApiError
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.orhanobut.hawk.Hawk
import retrofit2.Retrofit

interface BaseNetworkingListeners {
    companion object {
        val DEFAULT = object :
            BaseNetworkingListeners {
            override fun extraLogoutStep() {
                Log.e("BaseNetworkListeners", "No extra logout steps needed at the moment")
            }
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
    fun parseError(response: retrofit2.Response<*>) = ApiError().apply {
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

    /**
     * This logout function can be used to cleanup any resources the app is using.
     * i.e. remove all entries from Hawk, delete all data from Realm, ...
     *
     * After that it will call a Deeplink in the app to return to the 'Starting Activity' without any backstack.
     * For this to work, don't forget to add the intent filter to your 'Starting Activity' to make the deep
     *
     * ```
     *    <intent-filter>
     *        <action android:name="${applicationId}.logout" />
     *        <category android:name="android.intent.category.DEFAULT" />
     *    </intent-filter>
     * ```
     */
    fun logout() {
        // Using packageName for this so the application can differentiate between a develop, staging or production build and won't ask the user which to use
        val errorActivity = Intent("${Networking.getPackageName()}.logout")
        errorActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(CoreApp.getContext(), 22, errorActivity, 0)

        if (Hawk.isBuilt()) {
            Hawk.deleteAll()
        }

        extraLogoutStep()

        try {
            pendingIntent.send()
        } catch (e: PendingIntent.CanceledException) {
            e.initCause(Throwable("Make sure that the logout 'endpoint' got added as an intent-filter to your manifest!"))
            e.printStackTrace()
        }
    }

    /**
     * This function can be used when some extra things need to be reset, removed, deleted upon logout.
     *
     * For example, cancelling all network requests from all possible network clients can be listed in this function.
     * ```
     *      ProtectedRestClient.getHttpClient.dispatcher().cancelAll()
     * ```
     */
    fun extraLogoutStep()
}