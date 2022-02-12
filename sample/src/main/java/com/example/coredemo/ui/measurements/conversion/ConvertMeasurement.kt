package com.example.coredemo.ui.measurements.conversion

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.coredemo.R
import com.example.coredemo.databinding.ComponentMeasurementBinding

class ConvertMeasurement @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = ComponentMeasurementBinding.inflate(LayoutInflater.from(context), this)

    var unitTitle: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvUnitTitle.text = it
            }
        }
    var unitValue: String = "0"
        set(value) {
            field = value

            val text = unitValueEditText.editText?.text?.toString()
            if (text != value) {
                unitValueEditText.editText?.setText(value)
            }
        }
    var unitTotal: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvResult.text = it
            }
        }

    val unitValueEditText get() = binding.tilUnitValue
    val fromEditText get() = binding.tilFromUnit.editText
    val toEditText get() = binding.tilToUnit.editText

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.ConvertMeasurement).let {
            unitTitle = it.getString(R.styleable.ConvertMeasurement_unitTitle)
            unitValue = it.getString(R.styleable.ConvertMeasurement_unitValue) ?: "0"
            unitTotal = it.getString(R.styleable.ConvertMeasurement_unitTotal)

            it.recycle()
        }
    }
}

// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("unitValue")
fun setUnitValue(view: ConvertMeasurement, unitValue: String) {
    val oldVal = view.unitValueEditText.editText?.text?.toString() ?: ""
    if (oldVal == unitValue) return

    view.unitValue = unitValue
}

@InverseBindingAdapter(attribute = "unitValue", event = "unitValueAttrChanged")
fun getUnitValue(view: ConvertMeasurement): String {
    return view.unitValue
}

@BindingAdapter("unitValueAttrChanged")
fun unitValueChanged(view: ConvertMeasurement, listener: InverseBindingListener) {
    view.unitValueEditText.editText?.doOnTextChanged { text, _, _, _ ->
        val oldVal = view.unitValue
        if (oldVal == text.toString()) return@doOnTextChanged

        view.unitValue = text.toString()
        listener.onChange()
    }
}