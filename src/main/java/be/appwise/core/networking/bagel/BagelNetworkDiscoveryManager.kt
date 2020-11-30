package be.appwise.core.networking.bagel

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder

object BagelNetworkDiscoveryManager {

    //port and tag that bagle uses
    private const val SERVICE_TYPE: String = "_Bagel._tcp."
    private const val PORT = 43434
    private const val TAG = "BagelDiscoveryManager"

    private lateinit var nsdManager: NsdManager

    private var mServiceName: String? = null
    private var sockets = mutableListOf<Socket>()
    private var isRegistered = false

    private val registrationListener = object : NsdManager.RegistrationListener {

        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            // Save the service name. Android may have changed it in order to
            // resolve a conflict, so update the name you initially requested
            // with the name Android actually used.
            mServiceName = NsdServiceInfo.serviceName
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Registration failed! Put debugging code here to determine why.
            Log.d(TAG, "Registration failed errorcode $errorCode")
        }

        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            // Service has been unregistered. This only happens when you call
            // NsdManager.unregisterService() and pass in this listener.
            nsdManager.unregisterService(this)
            isRegistered = false
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Unregistration failed. Put debugging code here to determine why.
            Log.d(TAG, "Unregistration failed errorcode $errorCode")
        }
    }

    fun teardown() {
        nsdManager.apply {
            unregisterService(registrationListener)
            stopServiceDiscovery(discoveryListener)
        }
    }

    fun registerService(context: Context) {
        isRegistered = true
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = " "
            serviceType = SERVICE_TYPE
            port = PORT
        }

        nsdManager = (context.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
            discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    private val resolveListener = object : NsdManager.ResolveListener {

        private lateinit var mService: NsdServiceInfo

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")

            if (serviceInfo.serviceName == mServiceName) {
                Log.d(TAG, "Same IP.")
                return
            }
            mService = serviceInfo
            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host

            initializeServerSocket(host, port)
        }
    }

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "  discovery success $service")
            when {
                service.serviceType != SERVICE_TYPE -> // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, " Unknown Service Type: ${service.serviceType}")
                service.serviceName == mServiceName ->  // The name of the service tells the user what they'd be
                    Log.d(TAG, " Same machine: $mServiceName")

                service.serviceName.contains("MacBook") ->
                    nsdManager.resolveService(
                        service,
                        resolveListener
                    )
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "Service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
            //maybe try again
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }
    }

    private fun initializeServerSocket(host: InetAddress, port: Int) {
        // Initialize a server socket on the next available port.
        sockets.add(Socket(host, port))
    }

    fun sendMessage(bagelRequestMessage: BagelMessage) {
        if (isRegistered) {
            //other error messages -> maybe not initialized / registerservice not called
            GlobalScope.launch(Dispatchers.IO) {
                if (sockets.isEmpty())
                    Log.d(TAG, "BAGEL MESSAGE NOT SENT : no sockets found")
                sockets.forEach { socket ->
                    try {
                        val outPutStream = socket.getOutputStream()
                        val messageByteArray = Gson().toJson(bagelRequestMessage).toByteArray()
                        /*Logger.d("message to send \n ${Gson().toJson(bagelRequestMessage)}")*/
                        //first sent length of message to bagel
                        outPutStream.write(messageByteArray.size.toLong().toByteArray())
                        //sent actual message as a bytearray
                        outPutStream.write(messageByteArray)
                        outPutStream.flush()
                    } catch (ex: IOException) {
                        // to do check better management for disconnect en socket reset
                        ex.printStackTrace()
                        /*Logger.e("BAGEL MESSAGE NOT SENT : socket with channel : ${socket.channel} and port : ${socket.port} closed")*/
                        //use removeif because it uses iterator in background --> no concurrent modification exceptions because of deleting item used in sockets.foreach
                        sockets.removeIf { it == socket }
                    }
                }

            }
        } else {
            throw BagelNotRegisteredException()
        }
    }

    class BagelNotRegisteredException :
        java.lang.Exception("Bagel Network Discovery Manager is not registered! Before sending messages don't forget to enable it in your App class")

    private fun Long.toByteArray(): ByteArray {
        val bufferSize = Long.SIZE_BYTES
        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.order(ByteOrder.LITTLE_ENDIAN) // BIG_ENDIAN is default byte order, so it is not necessary.
        return buffer.putLong(this).array()
    }


}