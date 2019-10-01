package be.appwise.core.extensions.logging

import com.orhanobut.logger.Logger

// These extensions help us abstract the way we log things,
// should we ever need to change the Logging dependency to another one (i.e. Timber) then we only need to adjust it here, on one place


fun logd(message: String, vararg args: Any, tag: String = "") {
    Logger.t(tag)
        .d(message, args)
}

fun logd(args: Any, tag: String = "") {
    Logger.t(tag)
        .d(args)
}

fun loge(message:String, vararg args: Any, tag:String = ""){
    Logger.t(tag).e(message, args)
}

fun loge(throwable: Throwable?, message: String, vararg args:Any, tag:String=""){
    Logger.t(tag).e(throwable, message, args)
}

fun logw(message:String, vararg args: Any, tag:String = ""){
    Logger.t(tag).w(message, args)
}

fun logv(message:String, vararg args: Any, tag:String = ""){
    Logger.t(tag).v(message, args)
}

fun logi(message:String, vararg args: Any, tag:String = ""){
    Logger.t(tag).i(message, args)
}