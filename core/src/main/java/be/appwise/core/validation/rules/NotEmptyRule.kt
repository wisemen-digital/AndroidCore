package com.eevee.app.validators.sandbox.rules

class NotEmptyRule(
    val message: String = "This field cannot be empty"
) : EditTextRule(message) {
    override val textValidationRule = { text: String ->
        text.isNotEmpty()
    }
}
