package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class RegexMatchesRule<T: EditText>(
    val regex: Regex,
    val message: String = "Invalid value"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        regex.matches(text)
    }
}