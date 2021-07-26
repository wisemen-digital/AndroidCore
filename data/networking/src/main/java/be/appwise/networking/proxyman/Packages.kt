package be.appwise.networking.proxyman

import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets

/**
 * A package format that is used to give Proxyman all the needed information about
 * the app and device that is sending request and response data. This package allows
 * Proxyman to sort **api calls** by [Device] and then by [Project].
 *
 * @property device is the [Device] that is sending the request and response data
 * @property project is the [Project] that is sending the request and response data
 * @property icon is the base64 value of the launcher icon for your project/app
 * @constructor Creates an empty group.
 */

class ConnectionPackage() : Data {
    private val device: Device = Device.current
    val project = Project.current
    private val icon : String = getBase64Data()

    /**
     * @return The Base64 data for the app's launcher icon
     */

    private fun getBase64Data(): String {
        val drawable = ProxyManNetworkDiscoveryManager.getAppContext().packageManager.getApplicationIcon(project.bundleIdentifier)
        val bitmap = drawable.toBitmap()
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val byteArray: ByteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}

/**
 * A package format that is used to give Proxyman all the needed information to show the request and response data of 1 api call.
 *
 * @property request holds all request data off the network call
 * @property error holds all error data if their is an api error or if their is a Android specific errorr
 * @property response holds the reponse code and headers
 * @property responseBodyData holds a bytearray representing the response body data
 * @property packageType is the type of the package. Is a filter in the Proxyman client. Only http at the moment.
 * @property startAt Time when the request was sent (in seconds).
 * @property endAt Time when response was received (in seconds).
 * @constructor Creates a TrafficPackage.
 */
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

    /**
     * @return if the responsebody is to large to send
     */
    private fun isLargeResponseBody()  = responseBodyData?.count() ?: 0 > ProxyManNetworkDiscoveryManager.MaximumSizePackage
    /**
     * @return if the requestbody is to large to send
     */
    private fun isLargeRequestBody()  = request.body?.count() ?: 0 > ProxyManNetworkDiscoveryManager.MaximumSizePackage

    /**
     * updates the [TrafficPackage.error] of existing TrafficPackage with [exception]
     */
    fun updateWithCustomError(exception: Exception){
        this.error = CustomError(exception.hashCode(),exception.localizedMessage ?: "No error message")
    }

    /**
     * updates the [TrafficPackage.responseBodyData] of existing TrafficPackage with [response]
     * updates the [TrafficPackage.endAt] with the [response]'s receivedResponseAtMillis
     */
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
        this.endAt = response.receivedResponseAtMillis.div(1000.0)
    }

    /**
     * Converts [TrafficPackage] to the needed data format
     * It uses [ProxyManNetworkDiscoveryManager] , [Base64ArrayTypeAdapter] and [Base64TypeAdapter] to
     * Base64 encodes all nested fields with types [Data] and [ByteArray].
     * When the [responseBodyData] or the [request] are too big we replace them with "<Skip Large Response Body>"
     */
    override fun toData() = run {
        if (isLargeResponseBody())
            this.responseBodyData = "<Skip Large Response Body>".toByteArray()
        if (isLargeRequestBody())
            this.request.resetBody()
        ProxyManNetworkDiscoveryManager.proxyManGson.toJson(this)
    }
}

/**
 * The app that is sending the request and response data to Proxyman
 * @param name name of the project
 * @param bundleIdentifier package name of the project
 **/

class Project {
    companion object {
        val current = Project()
    }
    val name: String = ProxyManNetworkDiscoveryManager.getAppContext().applicationInfo.loadLabel(ProxyManNetworkDiscoveryManager.getAppContext().packageManager).toString()
    val bundleIdentifier: String = ProxyManNetworkDiscoveryManager.getAppContext().packageName
}

/**
 * The device/service that is sending the request and response data to Proxyman
 * @param name name of the device
 * @param model the model of the device , is used by Proxyman as readable/visible identifier for this device
 **/
class Device {
    companion object {
        val current = Device()
    }
    var name: String = ProxyManNetworkDiscoveryManager.getDeviceName() ?: Settings.Secure.getString(ProxyManNetworkDiscoveryManager.getAppContext().contentResolver, "bluetooth_name")
    val model: String = name + " (API " + Build.VERSION.SDK_INT+")"
}

/**
 * Represents a Android [Exception] that happened during the network call's lifecycle
 *
 * @property code code of the error (hashmap of exception)
 * @property message message of the error
 * @constructor Creates a custom error.
 **/
class CustomError(val code: Int, val message: String)

/**
 * The request of the networking call that's being sent to ProxyMan
 *
 * @property url url of the call
 * @property message method of the network call POST , GET , PUT
 * @property headers the request headers
 * @property body the request body
 * @constructor Creates an request.
 **/
class Request(
    var url: String,
    var method: String,
    var headers: Array<Header>,
    var body: ByteArray?
){
    /**
     * Replaces the request body with the bytearray for the string "<Skip Large Request Body>"
     * */
    fun resetBody() {
        body = "<Skip Large Request Body>".toByteArray()
    }
}
/**
 * The reponse of the networking call that's being sent to ProxyMan
 *
 * @property url code of the networking call 200,404,401,500,...
 * @property headers the response headers , list of *headers*
 * @constructor Creates an response.
 **/
class Response(var statusCode: Int, var headers: Array<Header>)

/**
 * @property key key / name of the header e.g. Accept-language
 * @property value value of the header e.g. nl
 * @constructor Creates an empty group.
 */
class Header(var key: String, var value: String)

interface Data {
    fun toData() = ProxyManNetworkDiscoveryManager.proxyManGson.toJson(this)
}