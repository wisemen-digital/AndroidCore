package com.eevee.app.validators.sandbox.rules

class EmailRule(
    val message: String = "This field must be a valid email"
) : EditTextRule(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,20}$"))
    }
}