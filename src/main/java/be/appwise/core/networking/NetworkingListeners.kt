package be.appwise.core.networking

import org.json.JSONException
import retrofit2.Response

interface NetworkingListeners{
    companion object{
        val NONE = object: NetworkingListeners{
            override fun errorListener(response: Response<*>): ApiError {
                throw Exception("Not implemented yet")
            }
        }
    }

    fun errorListener(response: Response<*>): ApiError {
        var error = ApiError()

        when (response.code()) {
            500 -> error.message = Networking.getContext().getString(be.appwise.core.R.string.api_error_fallback)
            404 -> error.message = Networking.getContext()
                .resources.getString(be.appwise.core.R.string.internet_connection_error)
            401 -> error.message = Networking.getContext()
                .resources.getString(be.appwise.core.R.string.api_error_authentication)
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
}