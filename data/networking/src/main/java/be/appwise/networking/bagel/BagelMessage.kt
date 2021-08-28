package be.appwise.networking.bagel

import android.util.Base64
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.StandardCharsets
import java.util.*

class BagelMessage {
    lateinit var device: Device
    lateinit var packetId: String
    lateinit var project: Project
    lateinit var requestInfo: RequestInfo

    data class Project(val projectName: String)
    data class Device(val deviceId: String, val deviceName: String, val deviceDescription: String)

    /**
     * use jsonobject as Headers and not string needs to be object
     * use jsonobject instead of pojo because you can't define variables with caps first letter and it's dynamic
     * requestbody can't be a nullable string
     **/
    data class RequestInfo(
        val requestMethod: String? = null,
        val url: String? = null,
        var requestBody: String = "",
        var requestHeaders: JsonObject? = null,
        var responseHeaders: JsonObject? = null,
        var responseData: String = "",
        var statusCode: String? = null,
        var startDate: Long = 0,
        var endDate: Long = 0
    )

    /**
     * Updates a BagelMessage with provided response data
     *
     * @see Networking
     *
     * @param response
     **/
    fun updateToMessageWithResponse(response: Response) = this.apply {
        requestInfo.statusCode = response.code.toString()
        requestInfo.startDate = response.sentRequestAtMillis / 1000
        requestInfo.endDate = response.receivedResponseAtMillis / 1000
        requestInfo.responseHeaders = getHeadersJson(response.headers)

        val responseBody = response.body
        val source = responseBody?.source()
        source?.let {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            requestInfo.responseData = getBase64(buffer)
        }
    }

    companion object {

        /**
         * Creates a BagelMessage with provided request data
         *
         * @param request The request body
         * @param applicationId The ID of the running application
         * @param deviceId The ID for the phone in Bagel
         * @param deviceName Readable Identifier used by Bagel to represent a device
         * @param deviceDescription Readable sub identifier used by Bagel for a device
         **/
        fun createRequestMessage(
            request: Request,
            applicationId: String,
            deviceId: String,
            deviceName: String,
            deviceDescription: String
        ) =
            BagelMessage().apply {

                device = Device(deviceId, deviceName, deviceDescription)
                //here we can use application id (packagename) or appname + buildflavor? --> what do you guys thinks ?
                project = Project(applicationId)

                requestInfo = RequestInfo(
                    request.method,
                    request.url.toString()
                )

                val requestBody = request.body
                requestBody?.let {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                    val requestBody64 = getBase64(buffer)
                    requestInfo.requestBody = requestBody64
                }

                requestInfo.requestHeaders = getHeadersJson(request.headers)

                packetId = UUID.randomUUID().toString()
            }
    }


    private fun getHeadersJson(headers: Headers) = JsonObject().apply {
        headers.names().sortedBy { it.toLowerCase(Locale.getDefault()) }.forEach { name ->
            this.addProperty(name, headers.values(name).first())
        }
    }

    private fun getBase64(buffer: Buffer) = Base64.encodeToString(
        buffer.clone().readString(StandardCharsets.UTF_8).toByteArray(),
        Base64.DEFAULT
    )

    /*example message
    {
      "device": {
        "deviceDescription": "iPhone iOS 14.0.1",
        "deviceId": "DJ's iPhone",
        "deviceName": "DJ's iPhone"
      },
      "packetId": "A3A51EF3-87E9-47F1-9050-F2CB6B7BB2CE",
      "project": {
        "projectName": "RemeCare - Development"
      },
      "requestInfo": {
        "endDate": 1604692119,
        "requestBody": "Y2xpZW50X2lkPWJjZTc3NDMxLTVmYWEtNDk2Mi04OTFjLTlhNDk1YjU0Mjg3MyZy\r\nZXNvdXJjZT1odHRwcyUzQSUyRiUyRmRldmFwaS5yZW1lY2FyZW5ldy5iZSUyRiZn\r\ncmFudF90eXBlPXBhc3N3b3JkJnBhc3N3b3JkPUFwcHdpc2UxJnVzZXJuYW1lPVBh\r\ndGllbnRIYXJ0Zi4xLkFwJTQwcmVtZWNhcmVkZXYubG9jYWw=",
        "requestHeaders": {
          "Accept": "application/json",
          "Accept-Encoding": "gzip, deflate, br",
          "Accept-Language": "en-gb",
          "Content-Length": "179",
          "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
        },
        "requestMethod": "POST",
        "startDate": 1604692119,
        "url": "https://devcas.remecare.be/adfs/oauth2/token"
      }
    }
    */
}