package be.appwise.networking

data class ProxyManConfig(
    val enabled: Boolean,
    val deviceName: String? = null,
    val allowedServices: ArrayList<String> = arrayListOf(),
    val isLoggingEnabled: Boolean = true
)
