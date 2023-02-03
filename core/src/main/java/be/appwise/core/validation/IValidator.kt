package be.appwise.core.validation

interface IValidator {
    fun validate(handleError: Boolean = false): ValidationResult
}
