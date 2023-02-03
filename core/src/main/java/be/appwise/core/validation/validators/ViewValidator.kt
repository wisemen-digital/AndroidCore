package be.appwise.core.validation.validators

import android.view.View
import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult
import be.appwise.core.validation.rules.ViewRule

open class ViewValidator<T : View>(
    val view: T,
    rulesBuilder: (ViewValidator<T>).() -> Unit
) : IValidator {

    protected val rules: MutableList<ViewRule<T>> = mutableListOf()

    init {
        rulesBuilder()
    }

    override fun validate(handleError: Boolean): ValidationResult {
        val validationResult = rules.foldRight(ValidationResult.Valid as ValidationResult) { rule, acc ->
            val isValid = rule.validationRule(view)
            val result = if (isValid) ValidationResult.Valid else ValidationResult.Invalid(setOf(rule.errorMessage))
            return@foldRight acc combine result
        }
        return validationResult
    }

    fun add(rule: ViewRule<T>) {
        this.rules.add(rule)
    }

    fun add(rules: List<ViewRule<T>>) {
        this.rules.addAll(rules)
    }

    fun remove(rule: ViewRule<T>) {
        this.rules.remove(rule)
    }

    fun remove(rules: List<ViewRule<T>>) {
        this.rules.removeAll(rules)
    }

}

