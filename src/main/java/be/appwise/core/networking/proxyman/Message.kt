package be.appwise.core.networking.proxyman

import android.os.Build
import android.provider.Settings


/**
 * if requestbody is filled in succes ?
 * */
class Message(
    val id: String,
    val messageType: String,
    val buildVersion: String = "0.9.0",
    var content: Data?
) {

    class Project {
        companion object {
            val current = Project()
        }

        val name: String = ProxyManNetworkDiscoveryManager.getAppContext().applicationInfo.loadLabel(ProxyManNetworkDiscoveryManager.getAppContext().packageManager).toString()
        val bundleIdentifier: String = ProxyManNetworkDiscoveryManager.getAppContext().packageName
    }

    class Device {
        companion object {
            val current = Device()
        }

        var name: String =
            Settings.Secure.getString(ProxyManNetworkDiscoveryManager.getAppContext().contentResolver, "bluetooth_name")
        val model: String =
            Build.MANUFACTURER + "," + Build.MODEL + " Android/" + Build.VERSION.SDK_INT
    }

    companion object {
        fun buildConnectionMessage(id: String, item: ConnectionPackage): Message {
            return Message(id, "connection", content = item)
        }

        fun buildTrafficMessage(id: String, item: TrafficPackage): Message {
            return Message(id, "traffic", content = item)
        }
    }

}


