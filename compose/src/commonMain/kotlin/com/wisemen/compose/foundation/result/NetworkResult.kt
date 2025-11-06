package com.wisemen.compose.foundation.result

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(
        val code: Int?,
        val message: String,
        val exception: Throwable? = null
    ) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    fun getOrNull(): T? = (this as? Success)?.data

    inline fun <R> map(transform: (T) -> R): NetworkResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> this
    }

    inline fun onSuccess(action: (T) -> Unit): NetworkResult<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (Int?, String, Throwable?) -> Unit): NetworkResult<T> {
        if (this is Error) action(code, message, exception)
        return this
    }

    fun toResource(): Resource<T> = when (this) {
        is Success -> Resource.Success(data)
        is Error -> Resource.Error(exception ?: Exception(message), message)
        is Loading -> Resource.Loading
    }

    companion object {
        fun <T> success(data: T): NetworkResult<T> = Success(data)

        fun error(
            code: Int? = null,
            message: String,
            exception: Throwable? = null
        ): NetworkResult<Nothing> = Error(code, message, exception)

        fun loading(): NetworkResult<Nothing> = Loading

        // Common network errors
        fun noInternet(): NetworkResult<Nothing> = error(
            message = "No internet connection"
        )

        fun timeout(): NetworkResult<Nothing> = error(
            code = 408,
            message = "Request timeout"
        )

        fun serverError(): NetworkResult<Nothing> = error(
            code = 500,
            message = "Internal server error"
        )

        fun unauthorized(): NetworkResult<Nothing> = error(
            code = 401,
            message = "Unauthorized"
        )
    }
}