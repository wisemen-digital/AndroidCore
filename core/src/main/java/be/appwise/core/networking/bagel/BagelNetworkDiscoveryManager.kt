package be.appwise.core.networking.bagel

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder

object BagelNetworkDiscoveryManager {
    private const val SERVICE_TYPE: String = "_Bagel._tcp."
    private const val TAG = "BagelDiscoveryManager"

    private var nsdManager: NsdManager? = null
    private var serviceInfoList = mutableListOf<NsdServiceInfo>()

    // isRegistered will be used to check if the discovery manager was initialized in the App class
    private var isRegistered = false

    fun teardown() {
        nsdManager?.apply {
            stopServiceDiscovery(discoveryListener)
        }
    }

    fun registerService(context: Context) {
        teardown()
        isRegistered = true

        nsdManager = (context.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    private val resolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")

            // Only add the serviceInfo if it isn't in the list already
            serviceInfoList.filter {
                serviceInfo.toString() == it.toString()
            }.apply {
                if (this.isEmpty()) {
                    serviceInfoList.add(serviceInfo)
                }
            }
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
            Log.d(TAG, "Discovery success $service")
            when {
                service.serviceType != SERVICE_TYPE -> {// Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: ${service.serviceType}")
                }
                service.serviceName.contains("MacBook") -> {
                    nsdManager?.resolveService(
                        service,
                        resolveListener
                    )
                }
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "Service lost: $service")

            // Can't remove the service from the list because we only get the "name" and "type"
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed on start: Error code: $errorCode")
            //maybe try again
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed on stop: Error code: $errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }
    }

    fun sendMessage(bagelRequestMessage: BagelMessage) {
        if (isRegistered) {
            // Other error messages -> maybe not initialized / registerservice not called
            GlobalScope.launch(Dispatchers.IO) {
                val iterator = serviceInfoList.listIterator()

                while (iterator.hasNext()) {
                    val item = iterator.next()
                    try {
                        val socket = Socket(item.host, item.port)
                        val outPutStream = socket.getOutputStream()
                        val messageByteArray = Gson().toJson(bagelRequestMessage).toByteArray()
                        Log.d(TAG, "message to send \n ${Gson().toJson(bagelRequestMessage)}")
                        // First sent length of message to bagel
                        outPutStream.write(messageByteArray.size.toLong().toByteArray())
                        // Sent actual message as a byteArray
                        outPutStream.write(messageByteArray)
                        outPutStream.flush()
                    } catch (ex: Exception) {
                        // An error occurred when trying to send a message to a socket, this can be because
                        // the socket isn't active anymore. Whatever reason it is, remove the socket from the
                        // list so further errors won't happen with the same socket.
                        iterator.remove()
                    }
                }
            }
        } else {
            throw BagelNotRegisteredException()
        }
    }

    class BagelNotRegisteredException :
        Exception("Bagel Network Discovery Manager is not registered! Before sending messages don't forget to enable it in your App class")

    private fun Long.toByteArray(): ByteArray {
        val bufferSize = Long.SIZE_BYTES
        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.order(ByteOrder.LITTLE_ENDIAN) // BIG_ENDIAN is default byte order, so it is not necessary.
        return buffer.putLong(this).array()
    }
}