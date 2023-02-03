package be.appwise.core.validation.validators

import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult

/**
 * A basic validator that can be used for multiple uses.
 * @param message the message that will be added to the [ValidationResult] when the validation fails
 * @param validateImpl the implementation of the validation
 */
class Validator(
    val message: String? = null,
    val validateImpl: () -> Boolean
) : IValidator {
    override fun validate(handleError: Boolean): ValidationResult {
        return if (validateImpl()) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(setOf(message).filterNotNull().toSet())
        }
    }
}