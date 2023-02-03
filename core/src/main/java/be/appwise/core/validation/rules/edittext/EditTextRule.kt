package be.appwise.core.validation.rules.edittext

import android.widget.EditText
import be.appwise.core.validation.rules.ViewRule

open class EditTextRule<T: EditText>(
    override val errorMessage: String,
    open val textValidationRule: (String) -> Boolean = { true }
) : ViewRule<EditText>() {


    override val validationRule = { view: EditText ->
        textValidationRule(view.text.toString())
    }

}
