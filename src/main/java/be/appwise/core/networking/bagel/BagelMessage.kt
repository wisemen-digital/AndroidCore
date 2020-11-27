package be.appwise.core.networking.bagel

import android.os.Build
import android.provider.Settings
import be.appwise.core.core.CoreApp
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*

class BagelMessage(
    var device: Device? = null,
    var packetId: String = "",
    var project: Project? = null,
    var requestInfo: RequestInfo? = null
) {

    class Project(val projectName: String)
    class Device(val deviceDescription: String, val deviceId: String, val deviceName: String)

    /**
     * use jsonobject as Headers and not string needs to be object
     * use jsonobject instead of pojo because you can't define variables with caps first letter and it's dynamic
     * requestbody can't be a nullable string
     * */
    class RequestInfo(
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

    fun updateToMessageWithReponse(response : Response) = this.apply {
        requestInfo?.statusCode = response.code().toString()
        requestInfo?.startDate = response.sentRequestAtMillis() / 1000
        requestInfo?.endDate = response.receivedResponseAtMillis() / 1000
        requestInfo?.responseHeaders = getHeadersJson(response.headers())

        val responseBody = response.body()
        val source = responseBody?.source()
        source?.let {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            requestInfo?.responseData = getBase64(buffer)
        }
    }

    companion object{
        fun createRequestMessage(request : Request,applicationId: String) = BagelMessage().apply {

            //unique identifier for phone
            val deviceId = Build.SERIAL
            //use bluetooth_name -- alot of people use their own name here phone - firstname lastname?
            val deviceName = Settings.Secure.getString(CoreApp.getContext().contentResolver, "bluetooth_name")
            //description we also sent to backend with our headerinterceptors
            val deviceDescription = Build.MANUFACTURER + "," + Build.MODEL + "; Android/" + Build.VERSION.SDK_INT

            device = Device(deviceDescription, deviceId, deviceName)
            //here we can use application id (packagename) or appname + buildflavor? --> what do you guys thinks ?
            project = Project(applicationId)
            /*project = Project(App.getContext().getString(R.string.app_name) + BuildConfig.FLAVOR + BuildConfig.BUILD_TYPE)*/

            requestInfo = RequestInfo(
                request.method(),
                request.url().toString()
            )

            val requestBody = request.body()
            requestBody?.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                val requestBody64 = getBase64(buffer)
                requestInfo?.requestBody = requestBody64
            }

            requestInfo?.requestHeaders = getHeadersJson(request.headers())

            packetId = UUID.randomUUID().toString()
        }
    }


    private fun getHeadersJson(headers: Headers) = JsonObject().apply {
        headers.names().forEach { name ->
            this.addProperty(name, headers.values(name).first())
        }
    }

    private val UTF8 = Charset.forName("UTF-8")
    private fun getBase64(buffer: Buffer) = Base64.getEncoder().encodeToString(buffer.clone().readString(UTF8).toByteArray())

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
    }*/
}