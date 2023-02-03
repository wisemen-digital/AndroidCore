package be.appwise.core.validation.rules

import android.widget.Switch

abstract class ViewRule<T> {
    abstract val errorMessage: String
    abstract val validationRule: (T) -> Boolean
}