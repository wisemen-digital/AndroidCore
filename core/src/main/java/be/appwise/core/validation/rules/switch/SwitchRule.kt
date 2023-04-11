package be.appwise.core.validation.rules.switch

import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import be.appwise.core.validation.rules.ViewRule

open class SwitchRule<T: SwitchCompat>(
    override val errorMessage: String,
    open val switchValidationRule: (T) -> Boolean = { true }
) : ViewRule<T>() {

    override val validationRule = { view: T ->
        switchValidationRule(view)
    }

}
