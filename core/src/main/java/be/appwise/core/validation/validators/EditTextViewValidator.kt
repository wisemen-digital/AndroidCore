package be.appwise.core.validation.validators

import android.widget.EditText
import be.appwise.core.validation.ValidationResult

/**
 * A validator for [EditText]s
 * @param view the [EditText] to validate
 * @param rulesBuilder the rules to validate the [EditText] with
 */
class EditTextViewValidator(
    view: EditText,
    rulesBuilder: ViewValidator<EditText>.() -> Unit
) : ViewValidator<EditText>(view, rulesBuilder) {

    /**
     * This implementation of the validate function will validate the view based on the rules that are added to this validator.
     * @param handleError if true, the error message will be shown on the view
     */
    override fun validate(handleError: Boolean): ValidationResult {

        val validationResult = rules.foldRight(ValidationResult.Valid as ValidationResult) { rule, acc ->
            val isValid = rule.validationRule(view)
            val result = if (isValid) ValidationResult.Valid else ValidationResult.Invalid(setOf(rule.errorMessage))
            return@foldRight acc combine result
        }

        if (handleError) {
            view.error = if (validationResult is ValidationResult.Invalid) validationResult.messages.first() else null
        }

        return validationResult
    }

}