package com.wisemen.compose.foundation.result

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    fun getOrNull(): T? = (this as? Success)?.data

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
        is Loading -> throw IllegalStateException("Resource is still loading")
    }

    fun getOrElse(defaultValue: @UnsafeVariance T): @UnsafeVariance T = getOrNull() ?: defaultValue

    inline fun <R> map(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> this
    }

    inline fun <R> flatMap(transform: (T) -> Resource<R>): Resource<R> = when (this) {
        is Success -> transform(data)
        is Error -> this
        is Loading -> this
    }

    inline fun onSuccess(action: (T) -> Unit): Resource<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (Throwable, String?) -> Unit): Resource<T> {
        if (this is Error) action(exception, message)
        return this
    }

    inline fun onLoading(action: () -> Unit): Resource<T> {
        if (this is Loading) action()
        return this
    }

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun error(exception: Throwable, message: String? = null): Resource<Nothing> = Error(exception, message)
        fun loading(): Resource<Nothing> = Loading

        inline fun <T> catch(block: () -> T): Resource<T> = try {
            success(block())
        } catch (e: Throwable) {
            error(e)
        }
    }
}