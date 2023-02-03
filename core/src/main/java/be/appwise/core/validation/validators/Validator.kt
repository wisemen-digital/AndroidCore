package be.appwise.core.validation.validators

import com.eevee.app.validators.sandbox.IValidator
import com.eevee.app.validators.sandbox.ValidationResult

class Validator(
    val message: String? = null,
    val validateImpl: () -> Boolean
) : IValidator {
    override fun validate(showErrors: Boolean): ValidationResult {
        return if (validateImpl()) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(setOf(message).filterNotNull().toSet())
        }
    }
}