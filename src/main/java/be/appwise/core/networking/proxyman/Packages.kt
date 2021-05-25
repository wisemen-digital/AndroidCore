package be.appwise.core.networking.proxyman

import android.graphics.Bitmap
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets


class ConnectionPackage() : Data {
    val device: Message.Device = Message.Device.current
    val project: Message.Project = Message.Project.current
    val icon: String = getBase64Data()

    private fun getBase64Data(): String {
        val drawable = ProxyManNetworkDiscoveryManager.getAppContext().packageManager.getApplicationIcon(project.bundleIdentifier)
        val bitmap = drawable.toBitmap()
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val byteArray: ByteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}


class TrafficPackage(
    var id: String,
    var request: Request,
    var error: CustomError? = null,
    var response: Response? = null,
    var responseBodyData: ByteArray? = null,
    var packageType: PackageType = PackageType.http,
    var startAt: Double,
    var endAt: Double? = null
) : Data {
    //startat and endat are fractional seconds
    enum class PackageType {
        http,
        websocket;
    }

    private fun isLargeResponseBody()  = responseBodyData?.count() ?: 0 > ProxyManNetworkDiscoveryManager.MaximumSizePackage
    private fun isLargeRequestBody()  = request.body?.count() ?: 0 > ProxyManNetworkDiscoveryManager.MaximumSizePackage


    fun updateWithCustomError(exception: Exception){
        this.error = CustomError(exception.hashCode(),exception.localizedMessage ?: "No error message")
    }

    fun updateWithReponseData(response: okhttp3.Response) {
        val responseHeaders = response.headers.map { Header(it.first, it.second) }
        val proxyManResponse = Response(response.code, responseHeaders.toTypedArray())
        this.response = proxyManResponse

        val responseBody = response.body
        val source = responseBody?.source()
        source?.let {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            this.responseBodyData = buffer.copy().readString(StandardCharsets.UTF_8).toByteArray()
        }

        /*this.error = CustomError(response.code,response.body.toString())*/
        this.endAt = System.currentTimeMillis() / 1000.0
    }


    override fun toData() = run {
        if (isLargeResponseBody())
            this.responseBodyData = "<Skip Large Response Body>".toByteArray()
        if (isLargeRequestBody())
            this.request.resetBody()
        ProxyManNetworkDiscoveryManager.proxyManGson.toJson(this)
    }
}

class CustomError(val code: Int, val message: String)

//body is base64
class Request(
    var url: String,
    var method: String,
    var headers: Array<Header>,
    var body: ByteArray?
){
    fun resetBody() {
        body = "<Skip Large Request Body>".toByteArray()
    }
}
class Response(var statusCode: Int, var headers: Array<Header>)
class Header(var key: String, var value: String)

interface Data {
    fun toData() = ProxyManNetworkDiscoveryManager.proxyManGson.toJson(this)
}