package be.appwise.networking.model

data class ApiError(
    val statusCode: Int? = null,
    val code: Int? = null,
    override val error: String? = null,
    override var status: Int? = null,
    override val error_description: String? = null,
    override val message: String? = null,
    override val errors: List<ErrorBody>? = null
) : BaseApiError {

    override fun toString() = asString
}

data class ErrorBody(
    override val code: String = "",
    override val detail: String = ""
) : BaseErrorBody

interface BaseErrorBody {
    val code: String
    val detail: String
}

interface BaseApiError {

    var status: Int?
    val error: String?

    // Cannot be serialized to something more readable because of the interface!
    val error_description: String?
    val message: String?
    val errors: List<BaseErrorBody>?

    /**
     * Can be overridden to parse it any other way..
     * Not every api returns just a message, so the message is optional.
     * [asString] is a placeholder so this base error class has a default way to show the error.
     */
    val asString
        get() = status?.let {
            "$status: $errorText"
        } ?: run {
            errorText ?: ""
        }

    val errorText get() = message ?: error_description ?: errorsAsMessage

    private val errorsAsMessage get() = errors?.joinToString(" ") { it.detail }
}