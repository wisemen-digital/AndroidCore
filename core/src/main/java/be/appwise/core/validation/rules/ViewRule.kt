package be.appwise.core.validation.rules

import android.view.View

/**
 * A template rule for a [View]
 * @property errorMessage the error message to show when the rule is not valid
 * @property validationRule the rule to validate the view with
 */
abstract class ViewRule<T : View> {
    abstract val errorMessage: String
    abstract val validationRule: (T) -> Boolean
}