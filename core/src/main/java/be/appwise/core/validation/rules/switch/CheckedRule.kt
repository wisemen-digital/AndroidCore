package be.appwise.core.validation.rules.switch

import androidx.appcompat.widget.SwitchCompat

class CheckedRule(
    val message: String = "Checkbox must be checked"
) : SwitchRule(message) {

    override val switchValidationRule = { switch : SwitchCompat ->
        switch.isChecked
    }
}
