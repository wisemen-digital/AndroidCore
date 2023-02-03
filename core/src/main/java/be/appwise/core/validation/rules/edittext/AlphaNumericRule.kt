package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class AlphaNumericRule(
    val message: String = "This field must contain only alphanumeric characters."
) : EditTextRule<EditText>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("[a-zA-Z0-9]+"))
    }
}
