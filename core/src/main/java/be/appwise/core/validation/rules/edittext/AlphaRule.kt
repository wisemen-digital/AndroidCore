package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class AlphaRule<T: EditText>(
    val message: String = "This field must contain only alpha characters."
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("[a-zA-Z]+"))
    }
}
