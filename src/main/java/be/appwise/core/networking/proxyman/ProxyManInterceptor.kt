package be.appwise.core.networking.proxyman

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.lang.Exception
import java.util.*

class ProxyManInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        //send request package
        val headers = request.headers.toMap().toMutableMap()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            headers.putIfAbsent("content-type", request.body?.contentType().toString())
            headers.putIfAbsent("content-length", request.body?.contentLength().toString())
        }
        else{
            if(!headers.containsKey("content-type"))
                headers.put("content-type", request.body?.contentType().toString())
            if(!headers.containsKey("content-length"))
                headers.put("content-length", request.body?.contentType().toString())
        }

        val proxyManHeaders = headers.map { Header(it.key, it.value) }.toTypedArray()

        val buffer = Buffer()
        request.body?.writeTo(buffer)
        val base64Body = buffer.readByteArray()


        val proxyManRequest = Request(
            request.url.toUri().toString(),
            request.method,
            proxyManHeaders,
            base64Body,
        )

        val trafficPackage = TrafficPackage(
            id = UUID.randomUUID().toString().toUpperCase(),
            request = proxyManRequest,
            startAt = System.currentTimeMillis() / 1000.0
        )

        /*val trafficMessage = ProxyManMessage.buildTrafficMessage(
           Configuration.default().id,
           trafficPackage
       )
      ProxyManNetworkDiscoveryManager.sendMessage(trafficMessage)*/

        //do the actual call not always succeeds so resent message here
        try {
            val response = chain.proceed(request)
            //update message with response data and send it to ProxyMan
            trafficPackage.updateWithReponseData(response)
            return response

        }
        catch (exception : Exception){
            trafficPackage.updateWithCustomError(exception)
            throw exception
        }
        finally {
            val trafficMessageResponse = Message.buildTrafficMessage(
                Configuration.default().id,
                trafficPackage
            )

            ProxyManNetworkDiscoveryManager.send(trafficMessageResponse)
        }
    }
}