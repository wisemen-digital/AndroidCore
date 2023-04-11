package be.appwise.core.validation.validators

import android.view.View
import be.appwise.core.validation.IValidator
import be.appwise.core.validation.ValidationResult
import be.appwise.core.validation.rules.ViewRule

/**
 * A validator that manages the validation of a view.
 * @property view the view that will be validated
 * @param rulesBuilder a builder that will be used to create the rules
 */
open class ViewValidator<T: View>(
    val view: T,
    rulesBuilder: (ViewValidator<T>).() -> Unit
) : IValidator {

    /**
     * The rules that will be used to validate the view.
     */
    protected val rules: MutableList<ViewRule<T>> = mutableListOf()

    init {
        rulesBuilder()
    }

    /**
     * This implementation of the validate function will validate the view based on the rules that are added to this validator.
     */
    override fun validate(handleError: Boolean): ValidationResult {
        val validationResult = rules.foldRight(ValidationResult.Valid as ValidationResult) { rule, acc ->
            val isValid = rule.validationRule(view)
            val result = if (isValid) ValidationResult.Valid else ValidationResult.Invalid(setOf(rule.errorMessage))
            return@foldRight acc combine result
        }
        return validationResult
    }

    /**
     * Adds a rule to the validator.
     * @param rule the rule that will be added
     */
    fun add(rule: ViewRule<T>) {
        this.rules.add(rule)
    }

    /**
     * Adds a list of rules to the validator.
     * @param rules the rules that will be added
     */
    fun add(rules: List<ViewRule<T>>) {
        this.rules.addAll(rules)
    }

    /**
     * Removes a rule from the validator.
     * @param rule the rule that will be removed
     */
    fun remove(rule: ViewRule<T>) {
        this.rules.remove(rule)
    }

    /**
     * Removes a list of rules from the validator.
     * @param rules the rules that will be removed
     */
    fun remove(rules: List<ViewRule<T>>) {
        this.rules.removeAll(rules)
    }

    /**
     * Removes all rules from the validator.
     */
    fun clear() {
        this.rules.clear()
    }

}

