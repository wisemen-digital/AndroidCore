package be.appwise.core.validation.rules.edittext

import android.widget.EditText
import be.appwise.core.validation.rules.ViewRule

/**
 * A rule for an [EditText]
 * @property errorMessage the error message to show when the rule is not valid
 * @property textValidationRule the rule to validate the text of the [EditText] with
 */
open class EditTextRule(
    override val errorMessage: String,
    open val textValidationRule: (String) -> Boolean = { true }
) : ViewRule<EditText>() {

    /**
     * The validation rule for the [EditText] that passes the text of the [EditText] to the [textValidationRule]
     */
    override val validationRule = { view: EditText ->
        textValidationRule(view.text.toString())
    }

}
