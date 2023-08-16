package be.appwise.networking

data class NetworkingConfig(
    val packageName: String = "",
    val clientSecret: String = "",
    val clientId: String = "",
    val appName: String = "",
    val versionName: String = "",
    val versionCode: String = "",
    val apiVersion: String = ""
)