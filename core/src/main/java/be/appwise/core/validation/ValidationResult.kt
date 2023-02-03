package com.eevee.app.validators.sandbox

sealed class ValidationResult {

    object Valid : ValidationResult()
    data class Invalid(val messages: Set<String>) : ValidationResult()

    infix fun combine(other: ValidationResult): ValidationResult {
        return when (other) {
            is Valid -> this
            is Invalid -> Invalid(((this as? Invalid)?.messages ?: emptySet()) + other.messages)
        }
    }

    val passed: Boolean
        get() = this is Valid

}