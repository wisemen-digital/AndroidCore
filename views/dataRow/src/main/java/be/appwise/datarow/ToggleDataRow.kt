package be.appwise.datarow

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.switchmaterial.SwitchMaterial

class ToggleDataRow @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    // you could also pass in "0" for "defStyleAttr" if you don't need any default values or anything
    defStyleAttr: Int = R.attr.toggleDataRowStyle
) : BaseDataRow(ctx, attributeSet, defStyleAttr) {

    companion object {
        private val DEF_STYLE_RES = R.style.Widget_Core_ToggleDataRow
    }

    // <editor-fold desc="Views">
    private var swValue: SwitchCompat

    val valueSwitch get() = swValue
    // </editor-fold>


    var checked: Boolean = false
        set(value) {
            field = value
            val bool = swValue.isChecked
            if (bool != value) {
                swValue.isChecked = value
            }
        }

    init {
        inflate(context, R.layout.toggle_data_row, this)

        tvLabel = findViewById(R.id.tvLabel)
        tvSubtitle = findViewById(R.id.tvSubtitle)
        clData = findViewById(R.id.clData)
        tvError = findViewById(R.id.tvError)
        swValue = findViewById(R.id.swValue)

        attributeSet?.let {
            val a = context.obtainStyledAttributes(
                it,
                R.styleable.ToggleDataRow,
                R.attr.toggleDataRowStyle,
                DEF_STYLE_RES
            )

            checked = a.getBoolean(R.styleable.ToggleDataRow_android_checked, false)

            paddingContent = a.getDimension(R.styleable.ToggleDataRow_paddingContent, 0f)
            paddingContentHorizontal = a.getDimension(R.styleable.ToggleDataRow_paddingContentHorizontal, INVALID_FLOAT)
            paddingContentVertical = a.getDimension(R.styleable.ToggleDataRow_paddingContentVertical, INVALID_FLOAT)

            showBoxError = a.getBoolean(R.styleable.ToggleDataRow_showBoxError, false)

            a.recycle()
        }

        // These should be done as last, at least after the allocation of the Views
        initBoxBackground()
        initError()

        clipChildren = false
        clipToPadding = false
    }
}

// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("android:checked")
fun setToggleValue(view: ToggleDataRow, bool: Boolean) {
    val oldVal = view.valueSwitch.isChecked
    if (oldVal == bool) return

    view.checked = bool
}

@InverseBindingAdapter(attribute = "android:checked", event = "android:checkedAttrChanged")
fun getToggleValue(view: ToggleDataRow): Boolean {
    return view.checked
}

@BindingAdapter("android:checkedAttrChanged")
fun checkedChanged(view: ToggleDataRow, listener: InverseBindingListener) {
    view.valueSwitch.setOnCheckedChangeListener { compoundButton, b ->
        val oldVal = view.checked
        if (oldVal == b) return@setOnCheckedChangeListener

        view.checked = b
        listener.onChange()
    }
}