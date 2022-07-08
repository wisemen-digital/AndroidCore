package be.appwise.networking

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.model.ApiError
import be.appwise.networking.model.BaseApiError
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.Response

// <editor-fold desc="Working with a custom Wrapper object of our own">
sealed interface CoreResponse<out S> {
    data class Success<out S>(val body: S, val response: Response<*>) : CoreResponse<S>

    sealed interface Error : CoreResponse<Nothing>

    data class GenericError(val response: Response<*>? = null, val error: BaseApiError? = null) : Error
    object NetworkError : Error
}

fun <S : Any> BaseViewModel.handleCoreResponse(response: CoreResponse<S>, shouldShowError: Boolean = true): S? {
    val throwable = when (response) {
        is CoreResponse.GenericError -> Throwable(response.error?.parseErrorMessage(response.response?.code()))
        is CoreResponse.NetworkError -> Throwable("Something went wrong, please try again later!")
        is CoreResponse.Success -> return response.body
    }

    if (shouldShowError) {
        setCoroutineException(throwable)
    }

    return null
}
// </editor-fold>

// <editor-fold desc="Working with the default `Response` wrapper of Retrofit itself">
fun <S : Any> BaseViewModel.handleResponse(response: Response<S>, shouldShowError: Boolean = true): S? {
    if (response.isSuccessful) {
        return response.body()
    } else {
        if (shouldShowError) {
            //TODO: add response code to `parseError` so we don't need `parseErrorMessage` anymore. Override the `toString()`
            setCoroutineException(Throwable(Networking.parseError(response).parseErrorMessage(response.code())))
        }
    }

    return null
}

suspend fun <S : Any> Response<S>.handleSuccessAndReturnResponse(onSuccess: suspend (S?) -> Unit): Response<S> {
    val response = this

    if (response.isSuccessful) {
        onSuccess(response.body())
    }

    return response
}
// </editor-fold>

// <editor-fold desc="Working with a CallAdapterFactory which wraps the response automatically">
typealias BaseResponse<T> = NetworkResponse<T, ApiError>

suspend fun <S : Any, E : BaseApiError> BaseViewModel.handleResponse(response: NetworkResponse<S, E>, shouldShowError: Boolean = true, shouldBlock: Boolean = true): S? {
    val throwable = when (response) {
        is NetworkResponse.Success -> return response.body
        is NetworkResponse.ServerError -> Throwable(response.body?.parseErrorMessage(response.code))
        is NetworkResponse.NetworkError -> response.error
        is NetworkResponse.UnknownError -> response.error
    }

    //TODO: is this the standard? That a call should block everything else?
    if (shouldBlock) {
        throw Exception(throwable)
    }

    if (shouldShowError) {
        setCoroutineException(throwable)
    }

    return null
}

suspend fun <S : Any, E : BaseApiError> NetworkResponse<S, E>.handleSuccessAndReturnResponse(onSuccess: suspend (S) -> Unit): NetworkResponse<S, E> {
    val response = this
    when (response) {
        is NetworkResponse.Success -> onSuccess(response.body)
        else -> {}
    }

    return response
}
// </editor-fold>
