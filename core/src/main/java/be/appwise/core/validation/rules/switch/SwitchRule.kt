package be.appwise.core.validation.rules.switch

import androidx.appcompat.widget.SwitchCompat
import be.appwise.core.validation.rules.ViewRule

open class SwitchRule(
    override val errorMessage: String,
    open val switchValidationRule: (SwitchCompat) -> Boolean = { true }
) : ViewRule<SwitchCompat>() {

    override val validationRule = { view: SwitchCompat ->
        switchValidationRule(view)
    }

}
