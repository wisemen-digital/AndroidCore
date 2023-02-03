package com.eevee.app.validators.sandbox.rules

import android.widget.Switch

abstract class ViewRule<T> {
    abstract val errorMessage: String
    abstract val validationRule: (T) -> Boolean
}

class isCheckedRule(
    override val errorMessage: String = "This field must be checked"
) : ViewRule<Switch>() {
    override val validationRule = { view: Switch ->
        view.isChecked
    }
}