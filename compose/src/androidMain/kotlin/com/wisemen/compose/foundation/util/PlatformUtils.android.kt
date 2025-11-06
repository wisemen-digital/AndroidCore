package com.wisemen.compose.foundation.util

import android.os.Build
import java.util.UUID

actual object PlatformUtils {
    actual val platformName: String = "Android"
    actual val isDebug: Boolean = Build.TYPE == "eng" || Build.TYPE == "userdebug"
    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
    actual fun randomUUID(): String = UUID.randomUUID().toString()
}

actual fun getCurrentPlatform(): Platform = Platform.ANDROID