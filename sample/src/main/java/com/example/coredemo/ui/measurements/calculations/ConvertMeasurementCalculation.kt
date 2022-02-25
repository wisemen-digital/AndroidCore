package com.example.coredemo.ui.measurements.calculations

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.coredemo.R
import com.example.coredemo.databinding.ComponentMeasurementCalculationBinding

class ConvertMeasurementCalculation @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = ComponentMeasurementCalculationBinding.inflate(LayoutInflater.from(context), this)

    var unitTitle: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvUnitTitle.text = it
            }
        }
    var unitFromValue: String = "0"
        set(value) {
            field = value

            val text = unitFromValueEditText.editText?.text?.toString()
            if (text != value) {
                unitFromValueEditText.editText?.setText(value)
            }
        }
    var unitToValue: String = "0"
        set(value) {
            field = value

            val text = unitToValueEditText.editText?.text?.toString()
            if (text != value) {
                unitToValueEditText.editText?.setText(value)
            }
        }
    var unitTotal: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvResult.text = it
            }
        }
    var convertToOriginalUnit: Boolean = false
        set(value) {
            field = value

            val bool = toggleSwitch.isChecked
            if (bool != value) {
                toggleSwitch.isChecked = value
            }
        }
    var calculationSymbol: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvCalculation.text = it
            }
        }
    val unitFromValueEditText get() = binding.tilFromUnitValue
    val unitToValueEditText get() = binding.tilToUnitValue
    val fromEditText get() = binding.tilFromUnit.editText
    val toEditText get() = binding.tilToUnit.editText
    val toggleSwitch get() = binding.swToggle

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.ConvertMeasurementCalculation).let {
            unitTitle = it.getString(R.styleable.ConvertMeasurementCalculation_unitTitle)
            unitFromValue = it.getString(R.styleable.ConvertMeasurementCalculation_unitFromValue) ?: "0"
            unitToValue = it.getString(R.styleable.ConvertMeasurementCalculation_unitToValue) ?: "0"
            unitTotal = it.getString(R.styleable.ConvertMeasurementCalculation_unitTotal)
            calculationSymbol = it.getString(R.styleable.ConvertMeasurementCalculation_calculationSymbol)

            it.recycle()
        }
    }
}

// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("unitFromValue")
fun setUnitFromValue(view: ConvertMeasurementCalculation, unitFromValue: String) {
    val oldVal = view.unitFromValueEditText.editText?.text?.toString() ?: ""
    if (oldVal == unitFromValue) return

    view.unitFromValue = unitFromValue
}

@InverseBindingAdapter(attribute = "unitFromValue", event = "unitFromValueAttrChanged")
fun getUnitFromValue(view: ConvertMeasurementCalculation): String {
    return view.unitFromValue
}

@BindingAdapter("unitFromValueAttrChanged")
fun unitFromValueChanged(view: ConvertMeasurementCalculation, listener: InverseBindingListener) {
    view.unitFromValueEditText.editText?.doOnTextChanged { text, _, _, _ ->
        val oldVal = view.unitFromValue
        if (oldVal == text.toString()) return@doOnTextChanged

        view.unitFromValue = text.toString()
        listener.onChange()
    }
}

// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("unitToValue")
fun setUnitToValue(view: ConvertMeasurementCalculation, unitToValue: String) {
    val oldVal = view.unitToValueEditText.editText?.text?.toString() ?: ""
    if (oldVal == unitToValue) return

    view.unitToValue = unitToValue
}

@InverseBindingAdapter(attribute = "unitToValue", event = "unitToValueAttrChanged")
fun getUnitToValue(view: ConvertMeasurementCalculation): String {
    return view.unitToValue
}

@BindingAdapter("unitToValueAttrChanged")
fun unitToValueChanged(view: ConvertMeasurementCalculation, listener: InverseBindingListener) {
    view.unitToValueEditText.editText?.doOnTextChanged { text, _, _, _ ->
        val oldVal = view.unitToValue
        if (oldVal == text.toString()) return@doOnTextChanged

        view.unitToValue = text.toString()
        listener.onChange()
    }
}

// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("convertToOriginalUnit")
fun setToggleValue(view: ConvertMeasurementCalculation, bool: Boolean) {
    val oldVal = view.toggleSwitch.isChecked
    if (oldVal == bool) return

    view.convertToOriginalUnit = bool
}

@InverseBindingAdapter(attribute = "convertToOriginalUnit", event = "convertToOriginalUnitAttrChanged")
fun getToggleValue(view: ConvertMeasurementCalculation): Boolean {
    return view.convertToOriginalUnit
}

@BindingAdapter("convertToOriginalUnitAttrChanged")
fun convertToOriginalUnitChanged(view: ConvertMeasurementCalculation, listener: InverseBindingListener) {
    view.toggleSwitch.setOnCheckedChangeListener { compoundButton, b ->
        val oldVal = view.convertToOriginalUnit
        if (oldVal == b) return@setOnCheckedChangeListener

        view.convertToOriginalUnit = b
        listener.onChange()
    }
}