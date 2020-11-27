package be.appwise.core.networking.bagel

import okhttp3.Interceptor
import okhttp3.Response

class BagelInterceptor(private val applicationId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val bagelRequestMessage = BagelMessage.createRequestMessage(request,applicationId)

        BagelNetworkDiscoveryManager.sendMessage(bagelRequestMessage)

        //do the actual call not always succeeds so resent message here
        val response = chain.proceed(request)

        bagelRequestMessage.updateToMessageWithReponse(response)

        BagelNetworkDiscoveryManager.sendMessage(bagelRequestMessage)

        return response
    }
}