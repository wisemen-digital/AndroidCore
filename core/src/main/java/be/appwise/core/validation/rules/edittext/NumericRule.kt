package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class NumericRule(
    val message: String = "This field must be a number"
) : EditTextRule<EditText>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("[0-9]+"))
    }
}
