package be.appwise.core.networking.proxyman

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
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
import java.util.zip.GZIPOutputStream


object ProxyManNetworkDiscoveryManager {

    const val MaximumSizePackage = 52428800

    val proxyManGson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, Base64ArrayTypeAdapter())
        .registerTypeAdapter(Data::class.java, Base64TypeAdapter())
        .create()

    private lateinit var nsdManager: NsdManager

    //port and tag that bagle uses
    private const val SERVICE_TYPE: String = "_Proxyman._tcp."
    private const val PORT = 43434
    private const val TAG = "ProxyManNetworkDiscoveryManager"

    private var mServiceName: String? = null

    private val registrationListener = object : NsdManager.RegistrationListener {

        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            // Save the service name. Android may have changed it in order to
            // resolve a conflict, so update the name you initially requested
            // with the name Android actually used.
            mServiceName = NsdServiceInfo.serviceName
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Registration failed! Put debugging code here to determine why.
            Logger.d("registrationfailed errorcode $errorCode")
        }

        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            // Service has been unregistered. This only happens when you call
            // NsdManager.unregisterService() and pass in this listener.
            nsdManager.unregisterService(this)
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Unregistration failed. Put debugging code here to determine why.
            Logger.d("unregistrationfailed errorcode $errorCode")
        }
    }

    fun teardown() {
        nsdManager.apply {
            unregisterService(registrationListener)
            stopServiceDiscovery(discoveryListener)
        }
    }

    private lateinit var mAppContext : Context
    fun getAppContext() = mAppContext

    fun registerService(appContext : Context) {
        mAppContext = appContext
        isRegistered = true
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = " "
            serviceType = SERVICE_TYPE
            port = PORT
        }

        nsdManager = (getAppContext().getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
        }

        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    private val resolveListener = object : NsdManager.ResolveListener {

        private lateinit var mService: NsdServiceInfo

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Logger.e("Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Logger.d("Resolve Succeeded. $serviceInfo")
            val serviceName: String = serviceInfo.serviceName
            if (serviceName == mServiceName) {
                Logger.d("Same IP.")
                return
            }
            mService = serviceInfo
            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host

            initializeServerSocket(serviceName, host, port)
        }
    }

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Logger.d("Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Logger.d("$TAG -->  discovery success $service")
            when {
                service.serviceType != SERVICE_TYPE -> // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Logger.d("$TAG --> Unknown Service Type: ${service.serviceType}")
                service.serviceName == mServiceName ->  // The name of the service tells the user what they'd be
                    Logger.d("$TAG --> Same machine: $mServiceName")

                service.serviceName.contains("Proxyman") ->
                    nsdManager.resolveService(
                        service,
                        resolveListener
                    )

            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            services.remove(service.serviceName)
            Logger.e("$TAG --> service lost: $service remaining sockets : ${services.size}")

        }

        override fun onDiscoveryStopped(serviceType: String) {
            Logger.i("$TAG --> Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Logger.e("$TAG --> Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
            //maybe try again
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Logger.e("$TAG --> Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }
    }

    private var services = HashMap<String, Socket>()

    private fun initializeServerSocket(serviceName: String, host: InetAddress, port: Int) {
        // Initialize a server socket on the next available port.
        services[serviceName] = Socket(host, port)
        sendAppData()
    }

    private var isRegistered = false
    private var pendingPackages = arrayListOf<Message>()

    fun send(proxymanRequestMessage: Message) {
        if (isRegistered) {
                //other error messages -> maybe not initialized / registerservice not called
                with(services.iterator()) {
                    forEach { service ->
                        val socket = service.value
                        try {
                            Logger.d("send message with id ${proxymanRequestMessage.messageType}")
                            val outPutStream = socket.getOutputStream()
                            val encodedMessage = gzip(proxyManGson.toJson(proxymanRequestMessage)) ?: proxyManGson.toJson(proxymanRequestMessage).toByteArray()
                            val output = ByteArrayOutputStream()
                            output.write(encodedMessage.size.toLong().toByteArray())
                            output.write(encodedMessage)
                            outPutStream.write(output.toByteArray())
                            outPutStream.flush()
                        } catch (ex: IOException) {
                            Logger.e("BAGEL MESSAGE NOT SENT : socket with channel : ${socket.channel} and port : ${socket.port} closed")
                            remove()
                            addMessageToPendingPackages(proxymanRequestMessage)
                        }
                    }
                    if (services.isEmpty()) {
                        Logger.d("BAGEL MESSAGE NOT SENT : no sockets found")
                        addMessageToPendingPackages(proxymanRequestMessage)
                    }
                }
        } else {
            throw ProxyManNotRegisteredException()
        }
    }

    /*private fun gzip(string: String): ByteArray? {
        return try {
            val data = Buffer()
            data.writeUtf8(string)
            val sink = Buffer()
            val gzipSink = GzipSink(sink)
            gzipSink.write(data, data.size)
            gzipSink.close()
            sink.readByteArray()
        } catch (ex: IOException){
            null
        }
    }*/
    private fun gzip(string: String): ByteArray? {
        val os = ByteArrayOutputStream(string.length)

        return try {
            val gos = GZIPOutputStream(os)
            gos.write(string.toByteArray())
            gos.close()
            val compressed: ByteArray = os.toByteArray()
            os.close()
            compressed
        } catch (ex: IOException){
            null
        }
    }

    class ProxyManNotRegisteredException : java.lang.Exception("Proxyman Network Discovery Manager is not registered! Before sending messages don't forget to enable it in your App class")

    private fun Long.toByteArray(): ByteArray {
        val bufferSize = Long.SIZE_BYTES
        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.order(ByteOrder.LITTLE_ENDIAN) // BIG_ENDIAN is default byte order, so it is not necessary.
        return buffer.putLong(this).array()
    }

    private fun addMessageToPendingPackages(message: Message) {
        pendingPackages.add(message)
    }

    private fun flushAllPendingIfNeeded() {
        if (pendingPackages.isEmpty()) return
        Logger.d("[Proxyman PoC] Flush ${pendingPackages.count()} items")
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