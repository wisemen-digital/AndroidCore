package be.appwise.core.validation

/**
 * The core of the validation library. Every class the implements this interface can used as a validator.
 */
interface IValidator {

    /**
     * Validates the input and returns a [ValidationResult]
     * @param handleError is used by specific validators to show errors to the user
     * @return [ValidationResult] the result of the validation
     */
    fun validate(handleError: Boolean = false): ValidationResult
}
