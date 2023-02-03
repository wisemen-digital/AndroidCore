package com.eevee.app.validators.sandbox

interface IValidator {
    fun validate(showErrors: Boolean = false): ValidationResult
}
