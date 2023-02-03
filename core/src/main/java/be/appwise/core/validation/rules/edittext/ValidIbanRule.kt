package be.appwise.core.validation.rules.edittext

import android.widget.EditText

class ValidIbanRule<T: EditText>(
    val message: String = "This is not a valid iban"
) : EditTextRule<T>(message) {
    override val textValidationRule = { text: String ->
        isIbanValid(text)
    }

    private fun isIbanValid(iban: String): Boolean {
        val IBAN_MIN_SIZE = 15
        val IBAN_MAX_SIZE = 34
        val IBAN_MAX: Long = 999999999
        val IBAN_MODULUS: Long = 97
        val trimmed = iban.trim().uppercase().filter { !it.isWhitespace() }
        if (trimmed.length < IBAN_MIN_SIZE || trimmed.length > IBAN_MAX_SIZE) {
            return false
        }
        val reformat = trimmed.substring(4) + trimmed.substring(0, 4)
        var total: Long = 0
        for (element in reformat) {
            val charValue = Character.getNumericValue(element)
            if (charValue < 0 || charValue > 35) {
                return false
            }
            total = (if (charValue > 9) total * 100 else total * 10) + charValue
            if (total > IBAN_MAX) {
                total %= IBAN_MODULUS
            }
        }
        return total % IBAN_MODULUS == 1L
    }
}
