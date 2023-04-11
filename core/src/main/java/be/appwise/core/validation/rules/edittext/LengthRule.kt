package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class LengthRule<T: EditText>(
    val min: Int,
    val max: Int,
    val message: String = "This length is not in range"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        require(min >= 0) { "Min must be non-negative, was $min" }
        require(max >= 0) { "Max must be non-negative, was $max" }
        require(min <= max) { "Min must be smaller than max" }
        text.length in min..max
    }
}
