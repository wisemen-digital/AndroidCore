package be.appwise.core.validation.rules.edittext

class NotEmptyRule(
    val message: String = "This field cannot be empty"
) : EditTextRule(message) {
    override val textValidationRule = { text: String ->
        text.isNotEmpty()
    }
}
