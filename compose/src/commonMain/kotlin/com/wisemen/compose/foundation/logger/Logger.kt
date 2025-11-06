package com.wisemen.compose.foundation.logger

enum class LogLevel(val priority: Int) {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ASSERT(7)
}

interface Logger {
    fun log(level: LogLevel, tag: String, message: String, throwable: Throwable? = null)

    fun v(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.VERBOSE, tag, message, throwable)

    fun d(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.DEBUG, tag, message, throwable)

    fun i(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.INFO, tag, message, throwable)

    fun w(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.WARN, tag, message, throwable)

    fun e(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.ERROR, tag, message, throwable)
}

object CoreLogger {
    private var logger: Logger = ConsoleLogger()
    private var minimumLogLevel: LogLevel = LogLevel.DEBUG

    fun setLogger(logger: Logger) {
        this.logger = logger
    }

    fun setMinimumLogLevel(level: LogLevel) {
        this.minimumLogLevel = level
    }

    fun log(level: LogLevel, tag: String, message: String, throwable: Throwable? = null) {
        if (level.priority >= minimumLogLevel.priority) {
            logger.log(level, tag, message, throwable)
        }
    }

    fun v(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.VERBOSE, tag, message, throwable)

    fun d(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.DEBUG, tag, message, throwable)

    fun i(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.INFO, tag, message, throwable)

    fun w(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.WARN, tag, message, throwable)

    fun e(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.ERROR, tag, message, throwable)
}

class ConsoleLogger : Logger {
    override fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        val levelString = when (level) {
            LogLevel.VERBOSE -> "V"
            LogLevel.DEBUG -> "D"
            LogLevel.INFO -> "I"
            LogLevel.WARN -> "W"
            LogLevel.ERROR -> "E"
            LogLevel.ASSERT -> "A"
        }

        val logMessage = "[$levelString/$tag] $message"
        println(logMessage)

        throwable?.let {
            println("[$levelString/$tag] Exception: ${it.message}")
            it.printStackTrace()
        }
    }
}

// Extension functions for easier logging
inline fun <reified T> T.logV(message: String, throwable: Throwable? = null) {
    CoreLogger.v(T::class.simpleName ?: "Unknown", message, throwable)
}

inline fun <reified T> T.logD(message: String, throwable: Throwable? = null) {
    CoreLogger.d(T::class.simpleName ?: "Unknown", message, throwable)
}

inline fun <reified T> T.logI(message: String, throwable: Throwable? = null) {
    CoreLogger.i(T::class.simpleName ?: "Unknown", message, throwable)
}

inline fun <reified T> T.logW(message: String, throwable: Throwable? = null) {
    CoreLogger.w(T::class.simpleName ?: "Unknown", message, throwable)
}

inline fun <reified T> T.logE(message: String, throwable: Throwable? = null) {
    CoreLogger.e(T::class.simpleName ?: "Unknown", message, throwable)
}