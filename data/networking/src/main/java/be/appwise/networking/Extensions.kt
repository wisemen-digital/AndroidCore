package be.appwise.networking

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.model.ApiError
import be.appwise.networking.responseAdapter.NetworkResponse

typealias BaseResponse<T> = NetworkResponse<T, ApiError>

fun <S : Any, E> BaseViewModel.handleResponse(response: NetworkResponse<S, E>, shouldShowError: Boolean = true, shouldBlock: Boolean = true): S? {
    val throwable = when (response) {
        is NetworkResponse.Success -> return response.body
        is NetworkResponse.ServerError -> Throwable(response.body?.toString())
        is NetworkResponse.NetworkError -> response.error
        is NetworkResponse.UnknownError -> response.error
    }

    if (shouldBlock) {
        throw throwable
    }

    if (shouldShowError) {
        setCoroutineException(throwable)
    }

    return null
}

suspend fun <S : Any, E> NetworkResponse<S, E>.handleSuccessAndReturnResponse(onSuccess: suspend (S) -> Unit): NetworkResponse<S, E> {
    val response = this
    when (response) {
        is NetworkResponse.Success -> onSuccess(response.body)
        else -> {}
    }

    return response
}