package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class NotEmptyOrBlankRule<T: EditText>(
    val message: String = "This field cannot be empty"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        text.isNotEmpty() && text.isNotBlank()
    }
}
