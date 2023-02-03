package be.appwise.core.validation.validators

import android.widget.EditText
import com.eevee.app.validators.sandbox.ValidationResult

class EditTextViewValidator(
    view: EditText,
    rulesBuilder: ViewValidator<EditText>.() -> Unit
) : ViewValidator<EditText>(view, rulesBuilder) {

    override fun validate(showErrors: Boolean): ValidationResult {
        val validationResult = rules.foldRight(ValidationResult.Valid as ValidationResult) { rule, acc ->
            val isValid = rule.validationRule(view)
            val result = if (isValid) ValidationResult.Valid else ValidationResult.Invalid(setOf(rule.errorMessage))
            return@foldRight acc combine result
        }
        if (showErrors) {
            view.error = if (validationResult is ValidationResult.Invalid) validationResult.messages.first() else null
        }
        return validationResult
    }
    
}