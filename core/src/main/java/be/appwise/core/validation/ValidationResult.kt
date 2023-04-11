package be.appwise.core.validation

/**
 * A [ValidationResult] is the result of a validation.
 * It can be either [Valid] or [Invalid].
 */
sealed class ValidationResult {

    /**
     * A [Valid] [ValidationResult] means that the validation was successful.
     */
    object Valid : ValidationResult()

    /**
     * An [Invalid] [ValidationResult] means that the validation failed.
     * It contains a set of error messages.
     */
    data class Invalid(val messages: Set<String>) : ValidationResult()

    /**
     * Combines two [ValidationResult]s. If one of them is [Invalid], the result will be [Invalid] and the messages are added.
     */
    infix fun combine(other: ValidationResult): ValidationResult {
        return when (other) {
            is Valid -> this
            is Invalid -> Invalid(((this as? Invalid)?.messages ?: emptySet()) + other.messages)
        }
    }

    /**
     * Returns true if the [ValidationResult] is [Valid].
     */
    val passed: Boolean
        get() = this is Valid

}