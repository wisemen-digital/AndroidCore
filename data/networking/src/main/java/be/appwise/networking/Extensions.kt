package be.appwise.networking

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.model.ApiError
import com.haroldadmin.cnradapter.NetworkResponse

fun <S : Any, E : ApiError> BaseViewModel.handleResponse(response: NetworkResponse<S, E>, shouldShowError: Boolean = true): S? {
    val throwable = when (response) {
        is NetworkResponse.Success -> return response.body
        is NetworkResponse.ServerError -> Throwable(response.body?.parseErrorMessage(response.code))
        is NetworkResponse.NetworkError -> response.error
        is NetworkResponse.UnknownError -> response.error
    }

    if (shouldShowError) {
        setCoroutineException(throwable)
    }

    return null
}

suspend fun <S : Any, E : ApiError> NetworkResponse<S, E>.handleSuccessAndReturnResponse(onSuccess: suspend (S) -> Unit): NetworkResponse<S, E> {
    val response = this
    when (response) {
        is NetworkResponse.Success -> onSuccess(response.body)
        else -> {}
    }

    return response
}
