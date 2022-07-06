package be.appwise.networking

import be.appwise.core.ui.base.BaseViewModel
import retrofit2.Response


fun <S : Any> BaseViewModel.handleResponse(response: Response<S>, shouldShowError: Boolean = true): S? {
    if (response.isSuccessful) {
        return response.body()
    } else {
        if (shouldShowError) {
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

//typealias BaseResponse<T> = NetworkResponse<T, ApiError>
//
//fun <S : Any, E : ApiError> BaseViewModel.handleResponse(response: NetworkResponse<S, E>, shouldShowError: Boolean = true): S? {
//    val throwable = when (response) {
//        is NetworkResponse.Success -> return response.body
//        is NetworkResponse.ServerError -> Throwable(response.body?.parseErrorMessage(response.code))
//        is NetworkResponse.NetworkError -> response.error
//        is NetworkResponse.UnknownError -> response.error
//    }
//
//    if (shouldShowError) {
//        setCoroutineException(throwable)
//    }
//
//    return null
//}
//
//suspend fun <S : Any, E : ApiError> NetworkResponse<S, E>.handleSuccessAndReturnResponse(onSuccess: suspend (S) -> Unit): NetworkResponse<S, E> {
//    val response = this
//    when (response) {
//        is NetworkResponse.Success -> onSuccess(response.body)
//        else -> {}
//    }
//
//    return response
//}
