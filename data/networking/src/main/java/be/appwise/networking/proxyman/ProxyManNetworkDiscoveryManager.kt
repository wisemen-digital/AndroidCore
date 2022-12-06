package be.appwise.networking.proxyman

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import java.util.zip.GZIPOutputStream
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

internal object ProxyManNetworkDiscoveryManager {


    /**
     * Discovery Manager that manages connecting , disconnecting with Proxyman services.
     * It also manages packages that were created when not connected with a ProxyMan service.
     * When it reconnects with a Proxyman service it flushed als the messages. [flushAllPendingIfNeeded]
     *
     * @property nsdManager
     * is the [NsdManager] that manages all listeners for Network discovery
     *
     * @property MaximumSizePackage
     * is the maximum allowed package size.
     * Used to check if [TrafficPackage.responseBodyData] or if [Request.body] are too big to be
     * sent to Proxyman
     *
     * @property proxyManGson
     * Used to connect [Base64ArrayTypeAdapter] and [Base64TypeAdapter]
     * In doing so making it possible to automatically Base64 encode objects with
     * the types [ByteArray] and [Data]
     *
     * @property SERVICE_TYPE
     * Tag sent by Proxyman over Network discovery , used to find only Proxyman Services
     *
     * @property TAG
     * Used as title for the logging of this DiscoveryManager
     *
     * @property mAppContext
     * The context of the App this DiscoveryManager is registered to.
     * Is used to deliver app specific data to the [TrafficPackage] and [ConnectionPackage] objects
     *
     * @property mDeviceName
     * Is used to override the default device name that is going to be shown in ProxyMan
     * @see Device
     *
     * @property mAllowedServices
     * A list of service names that is used to determine with which services this DiscoveryManager will connect.
     * Packages will only be sent to allowed services.
     *
     * @property maxPendingItem
     * The max amount of packages (calls) that can be hold in memory (in [pendingPackages]) when there aren't any clients available to receive them.
     *
     * @property mIsLoggingEnabled
     * Is used to determine if the Proxyman classes should show logging or not
     */

    const val MaximumSizePackage = 52428800

    val proxyManGson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, Base64ArrayTypeAdapter())
        .registerTypeAdapter(Data::class.java, Base64TypeAdapter())
        .create()

    private var nsdManager: NsdManager? = null

    private const val SERVICE_TYPE: String = "_Proxyman._tcp."
    private const val PORT = 43434
    private const val TAG = "ProxyManNetworkDiscoveryManager"

    private var mServiceName: String? = null


    /**
     * Disconnects unneeded listeners from stray nsdmanager
     * */
    private fun teardown() {
        nsdManager?.apply {
            stopServiceDiscovery(discoveryListener)
        }
    }

    private lateinit var mAppContext: Context
    private var mDeviceName: String? = null
    private lateinit var mAllowedServices: ArrayList<String>
    private var mIsLoggingEnabled: Boolean = true
    private const val maxPendingItem = 30

    fun getAppContext() = mAppContext
    fun getDeviceName() = mDeviceName

    fun registerService(
        appContext: Context,
        deviceName: String? = null,
        allowedServices: ArrayList<String>,
        isLoggingEnabled: Boolean = true,
    ) {

        teardown()
        mAppContext = appContext
        mDeviceName = deviceName
        mAllowedServices = allowedServices
        mIsRegistered = true
        mIsLoggingEnabled = isLoggingEnabled

        nsdManager = (getAppContext().getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    private class ProxyManResolveListener : NsdManager.ResolveListener {

        private lateinit var mService: NsdServiceInfo

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Logger.e("Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            showDebugMessage("Resolve Succeeded. $serviceInfo")
            val serviceName: String = serviceInfo.serviceName
            if (serviceName == mServiceName) {
                showDebugMessage("Same IP.")
                return
            }
            mService = serviceInfo
            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host

            initializeServerSocket(serviceName, host, port)
        }
    }


    private fun showDebugMessage(debugMessage: String) {
        if (mIsLoggingEnabled)
            Logger.t(TAG).d(debugMessage)
    }

    private fun showErrorMessage(debugMessage: String) {
        if (mIsLoggingEnabled)
            Logger.t(TAG).e(debugMessage)
    }

    private fun showInfoMessage(debugMessage: String) {
        if (mIsLoggingEnabled)
            Logger.t(TAG).i(debugMessage)
    }

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            showDebugMessage("Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            showDebugMessage("discovery success $service")
            val isProxymanService =
                if (mAllowedServices.isEmpty()) service.serviceName.contains("Proxyman-") else service.serviceName.substringAfter(
                    "Proxyman-").toLowerCase(Locale.ROOT) in mAllowedServices
            when {
                service.serviceType != SERVICE_TYPE -> // Service type is the string containing the protocol and
                    // transport layer for this service.
                    showDebugMessage("Unknown Service Type: ${service.serviceType}")
                service.serviceName == mServiceName ->  // The name of the service tells the user what they'd be
                    showDebugMessage("Same machine: $mServiceName")

                isProxymanService ->
                    nsdManager?.resolveService(
                        service,
                        ProxyManResolveListener()
                    )

            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            services.remove(service.serviceName)
            showErrorMessage("service lost: $service remaining sockets : ${services.size}")

        }

        override fun onDiscoveryStopped(serviceType: String) {
            showInfoMessage("Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            showErrorMessage("Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
            //maybe try again
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            showErrorMessage("Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }
    }

    private var services = HashMap<String, Socket>()

    private fun initializeServerSocket(serviceName: String, host: InetAddress, port: Int) {
        try {
            // Initialize a server socket on the next available port.
            services[serviceName] = Socket(host, port)
            sendAppData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var mIsRegistered = false
    private var pendingPackages = arrayListOf<Message>()

    fun send(proxymanRequestMessage: Message) {
        if (mIsRegistered) {
            //other error messages -> maybe not initialized / registerservice not called
            with(services.iterator()) {
                forEach { service ->
                    val socket = service.value
                    try {
                        showDebugMessage("send message with type ${proxymanRequestMessage.messageType}")
                        val outPutStream = socket.getOutputStream()
                        val encodedMessage = gzip(proxyManGson.toJson(proxymanRequestMessage))
                            ?: proxyManGson.toJson(proxymanRequestMessage).toByteArray()
                        val output = ByteArrayOutputStream()
                        output.write(encodedMessage.size.toLong().toByteArray())
                        output.write(encodedMessage)
                        outPutStream.write(output.toByteArray())
                        outPutStream.flush()
                    } catch (ex: IOException) {
                        showErrorMessage("Proxyman MESSAGE NOT SENT : socket with channel : ${socket.channel} and port : ${socket.port} closed")
                        addMessageToPendingPackages(proxymanRequestMessage)
                    }
                }
                if (services.isEmpty()) {
                    showDebugMessage("Proxyman MESSAGE NOT SENT : no sockets found")
                    addMessageToPendingPackages(proxymanRequestMessage)
                }
            }
        } else {
            throw ProxyManNotRegisteredException()
        }
    }

    private fun gzip(string: String): ByteArray? {
        val os = ByteArrayOutputStream(string.length)

        return try {
            val gos = GZIPOutputStream(os)
            gos.write(string.toByteArray())
            gos.close()
            val compressed: ByteArray = os.toByteArray()
            os.close()
            compressed
        } catch (ex: IOException) {
            null
        }
    }

    class ProxyManNotRegisteredException :
        java.lang.Exception("Proxyman Network Discovery Manager is not registered! Before sending messages don't forget to enable it in your App class")

    private fun Long.toByteArray(): ByteArray {
        val bufferSize = Long.SIZE_BYTES
        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.order(ByteOrder.LITTLE_ENDIAN) // BIG_ENDIAN is default byte order, so it is not necessary.
        return buffer.putLong(this).array()
    }

    private fun addMessageToPendingPackages(message: Message) {
        if (pendingPackages.count() >= maxPendingItem) {
            pendingPackages.clear()
        }
        pendingPackages.add(message)
    }

    private fun flushAllPendingIfNeeded() {
        if (pendingPackages.isEmpty()) return
        showDebugMessage("[Proxyman PoC] Flush ${pendingPackages.count()} items")
        GlobalScope.launch(Dispatchers.IO) {
            pendingPackages.listIterator().forEach {
                send(it)
            }
            pendingPackages.clear()
        }
    }

    private fun sendAppData() {
        //send app data (calls are sorted beneath it)
        val connectionMessage = Message.buildConnectionMessage(
            Configuration.default().id,
            ConnectionPackage()
        )
        pendingPackages.add(0, connectionMessage)
        flushAllPendingIfNeeded()
    }


}