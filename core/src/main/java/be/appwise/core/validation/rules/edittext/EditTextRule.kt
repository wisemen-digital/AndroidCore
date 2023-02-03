package be.appwise.core.validation.rules.edittext

import android.widget.EditText
import com.eevee.app.validators.sandbox.rules.ViewRule

open class EditTextRule(
    override val errorMessage: String,
    open val textValidationRule: (String) -> Boolean = { true }
) : ViewRule<EditText>() {


    override val validationRule = { view: EditText ->
        textValidationRule(view.text.toString())
    }

}
