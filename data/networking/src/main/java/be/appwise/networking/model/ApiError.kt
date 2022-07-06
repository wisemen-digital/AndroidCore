package be.appwise.networking.model

open class ApiError(
    @Transient
    open val message: String
) {

    /**
     * Can be overridden to parse it any other way..
     * Not every api returns just a message, so the message is optional.
     * the [parseErrorMessage] is a placeholder so this base error class has a default way to show the error.
     */
    open fun parseErrorMessage(responseCode: Int? = null) =
        responseCode?.let {
            "$responseCode: $message"
        } ?: run {
            message
        }
}