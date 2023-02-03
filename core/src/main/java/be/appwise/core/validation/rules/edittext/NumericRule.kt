package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class NumericRule<T: EditText>(
    val message: String = "This field must be a number"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("[0-9]+"))
    }
}
