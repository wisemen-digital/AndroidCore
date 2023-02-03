package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class AlphaRule(
    val message: String = "This field must contain only alpha characters."
) : EditTextRule<EditText>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("[a-zA-Z]+"))
    }
}
