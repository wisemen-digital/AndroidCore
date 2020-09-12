package be.appwise.core.extensions.logging

import com.orhanobut.logger.Logger

// These extensions help us abstract the way we log things,
// should we ever need to change the Logging dependency to another one (i.e. Timber) then we only need to adjust it here, on one place


fun logd(tag: String? = "", message: String, vararg args: Any) {
    Logger.t(tag)
        .d(message, args)
}

fun logd(tag: String? = "", anyObject: Any) {
    Logger.t(tag)
        .d(anyObject)
}

fun loge(tag: String? = "", message: String, vararg args: Any) {
    Logger.t(tag).e(message, args)
}

fun loge(tag: String? = "", throwable: Throwable?, message: String, vararg args: Any) {
    Logger.t(tag).e(throwable, message, args)
}

fun logw(tag: String? = "", message: String, vararg args: Any) {
    Logger.t(tag).w(message, args)
}

fun logv(tag: String? = "", message: String, vararg args: Any) {
    Logger.t(tag).v(message, args)
}

fun logi(tag: String? = "", message: String, vararg args: Any) {
    Logger.t(tag).i(message, args)
}