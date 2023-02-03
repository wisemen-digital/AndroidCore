package com.eevee.app.validators.sandbox.rules

import android.widget.EditText

open class EditTextRule(
    override val errorMessage: String,
    open val textValidationRule: (String) -> Boolean = { true }
) : ViewRule<EditText>() {


    override val validationRule = { view: EditText ->
        textValidationRule(view.text.toString())
    }

}
