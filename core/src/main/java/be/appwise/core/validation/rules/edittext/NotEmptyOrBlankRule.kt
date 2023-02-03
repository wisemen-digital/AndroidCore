package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class NotEmptyOrBlankRule(
    val message: String = "This field cannot be empty"
) : EditTextRule<EditText>(message) {
    override val textValidationRule = { text: String ->
        text.isNotEmpty() && text.isNotBlank()
    }
}
