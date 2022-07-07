package be.appwise.networking.base

import be.appwise.networking.CoreResponse
import be.appwise.networking.Networking
import be.appwise.networking.R
import be.appwise.networking.model.ApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
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
                    throw Exception(parseError(response)?.parseErrorMessage(response.code()))
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception(Networking.getContext().getString(R.string.internet_connection_error))
        }
    }

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>, onSuccess: () -> Unit = {}): CoreResponse<T> {
        return try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                parseSuccessfulResponse(response).also {
                    if (it is CoreResponse.Success) {
                        onSuccess()
                    }
                }
            } else {
                parseUnsuccessfulResponse(response)
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> CoreResponse.NetworkError
                is HttpException -> {
                    val response = e.response()
                    CoreResponse.GenericError(response, parseError(e.response() as Response<*>))
                }
                else -> CoreResponse.GenericError(null, ApiError(e.message ?: ""))
            }
        }
    }

    private fun <S> parseUnsuccessfulResponse(
        response: Response<S>
    ): CoreResponse<S> {
        return CoreResponse.GenericError(response, parseError(response))
    }

    private fun <S> parseSuccessfulResponse(
        response: Response<S>,
    ): CoreResponse<S> {
        val responseBody: S? = response.body()
        if (responseBody == null) {
            if (responseBody is Unit) {
                @Suppress("UNCHECKED_CAST")
                return CoreResponse.Success(Unit, response) as CoreResponse<S>
            }

            return CoreResponse.GenericError(response)
        }

        return CoreResponse.Success(responseBody, response)
    }

    /**
     * Uses the [Networking.parseError] by default, can be overridden in the Repo if you need anything special.
     */
    fun parseError(response: Response<*>): ApiError? {
        return try {
            Networking.parseError(response)
        } catch (e: Exception) {
            null
        }
    }
}