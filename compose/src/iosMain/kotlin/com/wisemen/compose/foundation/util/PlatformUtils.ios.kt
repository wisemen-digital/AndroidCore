package com.wisemen.compose.foundation.util

import platform.Foundation.NSDate
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970
import kotlin.experimental.ExperimentalNativeApi

actual object PlatformUtils {
    actual val platformName: String = "iOS"
    actual val isDebug: Boolean = false // Can be determined differently in iOS
    actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
    actual fun randomUUID(): String = NSUUID().UUIDString()
}

@OptIn(ExperimentalNativeApi::class)
actual fun getCurrentPlatform(): Platform = Platform.IOS