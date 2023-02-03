package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class EmailRule<T: EditText>(
    val message: String = "This field must be a valid email"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        text.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]$"))
    }
}