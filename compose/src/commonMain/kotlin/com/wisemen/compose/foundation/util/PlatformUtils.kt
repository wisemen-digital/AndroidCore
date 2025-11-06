package com.wisemen.compose.foundation.util

expect object PlatformUtils {
    val platformName: String
    val isDebug: Boolean
    fun currentTimeMillis(): Long
    fun randomUUID(): String
}

enum class Platform {
    ANDROID,
    IOS,
    DESKTOP,
    WEB
}

expect fun getCurrentPlatform(): Platform