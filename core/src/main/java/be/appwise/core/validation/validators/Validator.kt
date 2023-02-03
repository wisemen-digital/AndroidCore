package be.appwise.core.validation.validators

import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult

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