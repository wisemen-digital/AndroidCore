package be.appwise.core.networking

import retrofit2.Response

class ImplNetworkingListeners : NetworkingListeners {
    override fun errorListener(response: Response<*>): ApiError {
        return super.errorListener(response)
    }
}