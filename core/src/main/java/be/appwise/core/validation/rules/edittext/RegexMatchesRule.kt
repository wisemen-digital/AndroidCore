package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class RegexMatchesRule(
    val regex: Regex,
    val message: String = "Invalid value"
) : EditTextRule<EditText>(message) {
    override val textValidationRule = { text: String ->
        regex.matches(text)
    }
}