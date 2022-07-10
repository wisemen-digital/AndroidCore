package be.appwise.networking.model

data class ApiError(override val message: String, override var responseCode: Int?) : BaseApiError {
    override fun toString() = asString
}

interface BaseApiError {

    val message: String
    var responseCode: Int?

    /**
     * Can be overridden to parse it any other way..
     * Not every api returns just a message, so the message is optional.
     * [asString] is a placeholder so this base error class has a default way to show the error.
     */
    val asString
        get() = responseCode?.let {
            "$responseCode: $message"
        } ?: run {
            message
        }
}