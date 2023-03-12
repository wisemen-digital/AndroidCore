package be.appwise.networking.bagel

import okhttp3.Interceptor
import okhttp3.Response

@Deprecated("Please start using Proxyman instead of Bagel")
class BagelInterceptor(private val applicationId: String,private val deviceId : String, private val deviceName : String, private val deviceDescription : String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Sends message to bagel with the request info
        val bagelRequestMessage = BagelMessage.createRequestMessage(request, applicationId,deviceId, deviceName, deviceDescription)
        BagelNetworkDiscoveryManager.sendMessage(bagelRequestMessage)

        // After you call this you will have the response of request if it succeeds
        val response = chain.proceed(request)

        // Update message to bagel with response data from request
        bagelRequestMessage.updateToMessageWithResponse(response)
        BagelNetworkDiscoveryManager.sendMessage(bagelRequestMessage)

        return response
    }
}