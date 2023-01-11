package be.appwise.networking.base

import be.appwise.networking.Networking
import be.appwise.networking.R
import be.appwise.networking.model.BaseApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

interface BaseRepository {
    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    suspend fun <T : Any> doCall(call: Call<T>): T {
        return try {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    throw Exception(parseError(response)?.toString())
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception(Networking.getContext().getString(R.string.internet_connection_error))
        }
    }

    /**
     * Uses the [Networking.parseError] by default, can be overridden in the Repo if you need anything special.
     */
    fun parseError(response: Response<*>): BaseApiError? {
        return try {
            Networking.parseError(response)
        } catch (e: Exception) {
            null
        }
    }
}