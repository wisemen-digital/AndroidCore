package be.appwise.core.validation.rules.switch

import androidx.appcompat.widget.SwitchCompat

class CheckedRule<T: SwitchCompat>(
    val message: String = "Checkbox must be checked"
) : SwitchRule<T>(message) {

    override val switchValidationRule = { switch : T ->
        switch.isChecked
    }
}
