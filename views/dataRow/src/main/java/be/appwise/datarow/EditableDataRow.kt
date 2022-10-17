package be.appwise.datarow

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputEditText

@Suppress("MemberVisibilityCanBePrivate")
class EditableDataRow @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    // you could also pass in "0" for "defStyleAttr" if you don't need any default values or anything
    defStyleAttr: Int = R.attr.editableDataRowStyle
) : BaseDataRow(ctx, attributeSet, defStyleAttr) {

    companion object {
        private val DEF_STYLE_RES = R.style.Widget_Core_EditableDataRow
    }

    // <editor-fold desc="Views">
    private var etValue: TextInputEditText
    private var tvPrefix: TextView
    private var tvSuffix: TextView

    val valueEditText get() = etValue
    // </editor-fold>

    // <editor-fold desc="Texts">
    var valueText: CharSequence = ""
        set(value) {
            field = value
            val text = etValue.text?.toString()
            if (text != value) {
                etValue.setText(value)
            }
        }
    var hintText: CharSequence = ""
        set(value) {
            field = value
            etValue.hint = value
        }

    var prefixText: CharSequence? = ""
        set(value) {
            field = value
            tvPrefix.text = value
            tvPrefix.isVisible = value?.isNotBlank() == true
        }

    var suffixText: CharSequence? = ""
        set(value) {
            field = value
            tvSuffix.text = value
            tvSuffix.isVisible = value?.isNotBlank() == true
        }
    // </editor-fold>

    // <editor-fold desc="TextAppearances">
    @StyleRes
    var valueTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateValueEditTextAppearance()
        }

    @StyleRes
    var prefixTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            setTextAppearanceOnTextView(tvPrefix, value)
        }

    @StyleRes
    var suffixTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            setTextAppearanceOnTextView(tvSuffix, value)
        }
    // </editor-fold>

    // <editor-fold desc="TextColor">
    var hintTextColor: ColorStateList? = null
        set(value) {
            field = value
            value?.let(etValue::setHintTextColor)
        }

    var valueTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateValueEditTextAppearance()
        }

    var prefixTextColor: ColorStateList? = null
        set(value) {
            field = value
            setTextColorOnTextView(tvPrefix, value)
        }

    var suffixTextColor: ColorStateList? = null
        set(value) {
            field = value
            setTextColorOnTextView(tvSuffix, value)
        }
    // </editor-fold>

    private var suffixWidth: Float = INVALID_FLOAT
        set(value) {
            field = value
            if (value != INVALID_FLOAT) {
                tvSuffix.width = value.toInt()
            }
        }

    // <editor-fold desc="EditBox Shape">
    private lateinit var editBoxShapeAppearanceModel: ShapeAppearanceModel
    private var editBoxBackgroundDrawable: MaterialShapeDrawable? = null

    private var showErrorStrokeOnEditBox: Boolean = true
    private var editBoxBackgroundColor: ColorStateList? = null
    private var editBoxStrokeColor: ColorStateList? = null
    private var editBoxStrokeErrorColor: ColorStateList? = null
    private var editBoxStrokeWidth: Float = 0f
    private var editBoxPaddingContent: Float = 0f
    private var editBoxPaddingContentHorizontal: Float = 0f
    private var editBoxPaddingContentVertical: Float = 0f
    private var editBoxElevation: Float = 0f

    var editBoxCornerRadius: Float = 0f
        set(value) {
            field = value
            updateEditBoxCornerSizes(value)
        }
    var editBoxCornerRadiusTopStart: Float = 0f
        set(value) {
            field = value
            updateEditBoxCornerSizes(topLeft = value)
        }
    var editBoxCornerRadiusTopEnd: Float = 0f
        set(value) {
            field = value
            updateEditBoxCornerSizes(topRight = value)
        }
    var editBoxCornerRadiusBottomEnd: Float = 0f
        set(value) {
            field = value
            updateEditBoxCornerSizes(bottomRight = value)
        }
    var editBoxCornerRadiusBottomStart: Float = 0f
        set(value) {
            field = value
            updateEditBoxCornerSizes(bottomLeft = value)
        }
    // </editor-fold>

    var inputType: Int = EditorInfo.TYPE_CLASS_TEXT
        set(value) {
            field = value
            etValue.inputType = value
        }

    var editBoxGravity: Int = Gravity.CENTER
        set(value) {
            field = value
            etValue.gravity = value
        }

    init {
        inflate(context, R.layout.editable_data_row, this)

        tvLabel = findViewById(R.id.tvLabel)
        tvSubtitle = findViewById(R.id.tvSubtitle)
        clData = findViewById(R.id.clData)
        tvError = findViewById(R.id.tvError)
        etValue = findViewById(R.id.etValue)
        tvPrefix = findViewById(R.id.tvPrefix)
        tvSuffix = findViewById(R.id.tvSuffix)

        attributeSet?.let {
            val a = context.obtainStyledAttributes(
                it,
                R.styleable.EditableDataRow,
                R.attr.editableDataRowStyle,
                DEF_STYLE_RES
            )

            // <editor-fold desc="Get all attributes">
            valueText = a.getText(R.styleable.EditableDataRow_valueText) ?: ""
            hintText = a.getText(R.styleable.EditableDataRow_hintText) ?: ""
            prefixText = a.getText(R.styleable.EditableDataRow_prefixText) ?: ""
            suffixText = a.getText(R.styleable.EditableDataRow_suffixText) ?: ""
            // </editor-fold>

            // <editor-fold desc="TextAppearances">
            valueTextAppearance = a.getResourceId(R.styleable.EditableDataRow_valueTextAppearance, INVALID_INT)
            prefixTextAppearance = a.getResourceId(R.styleable.EditableDataRow_prefixTextAppearance, INVALID_INT)
            suffixTextAppearance = a.getResourceId(R.styleable.EditableDataRow_suffixTextAppearance, INVALID_INT)
            // </editor-fold>

            // <editor-fold desc="TextColors">

            // Getting the colors should be done after the TextAppearance,
            // this is to make sure a specific color takes precedence before an Appearance
            hintTextColor = a.getColorStateList(R.styleable.EditableDataRow_hintTextColor)
            valueTextColor = a.getColorStateList(R.styleable.EditableDataRow_valueTextColor)
            prefixTextColor = a.getColorStateList(R.styleable.EditableDataRow_prefixTextColor)
            suffixTextColor = a.getColorStateList(R.styleable.EditableDataRow_suffixTextColor)
            // </editor-fold>

            suffixWidth = a.getDimension(R.styleable.EditableDataRow_suffixWidth, INVALID_FLOAT)

            paddingContent = a.getDimension(R.styleable.EditableDataRow_paddingContent, 0f)
            paddingContentHorizontal = a.getDimension(R.styleable.EditableDataRow_paddingContentHorizontal, INVALID_FLOAT)
            paddingContentVertical = a.getDimension(R.styleable.EditableDataRow_paddingContentVertical, INVALID_FLOAT)

            editBoxStrokeErrorColor = a.getColorStateList(R.styleable.EditableDataRow_editBoxStrokeErrorColor)
            editBoxStrokeColor = a.getColorStateList(R.styleable.EditableDataRow_editBoxStrokeColor)

            editBoxPaddingContent = a.getDimension(R.styleable.EditableDataRow_editBoxPaddingContent, 0f)
            editBoxPaddingContentHorizontal = a.getDimension(R.styleable.EditableDataRow_editBoxPaddingContentHorizontal, INVALID_FLOAT)
            editBoxPaddingContentVertical = a.getDimension(R.styleable.EditableDataRow_editBoxPaddingContentVertical, INVALID_FLOAT)
            editBoxElevation = a.getDimension(R.styleable.EditableDataRow_editBoxElevation, 0f)
            editBoxStrokeWidth = a.getDimension(R.styleable.EditableDataRow_editBoxStrokeWidth, 0f)

            // <editor-fold desc="Background">
            editBoxShapeAppearanceModel = ShapeAppearanceModel.builder(context, attributeSet, defStyleAttr, DEF_STYLE_RES).build()
            editBoxCornerRadius = a.getDimension(R.styleable.EditableDataRow_editBoxCornerRadius, INVALID_FLOAT)
            editBoxCornerRadiusTopStart = a.getDimension(R.styleable.EditableDataRow_editBoxCornerRadiusTopStart, INVALID_FLOAT)
            editBoxCornerRadiusTopEnd = a.getDimension(R.styleable.EditableDataRow_editBoxCornerRadiusTopEnd, INVALID_FLOAT)
            editBoxCornerRadiusBottomEnd = a.getDimension(R.styleable.EditableDataRow_editBoxCornerRadiusBottomEnd, INVALID_FLOAT)
            editBoxCornerRadiusBottomStart = a.getDimension(R.styleable.EditableDataRow_editBoxCornerRadiusBottomStart, INVALID_FLOAT)
            // </editor-fold>

            editBoxBackgroundColor = a.getColorStateList(R.styleable.EditableDataRow_editBoxBackgroundColor)

            showErrorStrokeOnEditBox = a.getBoolean(R.styleable.EditableDataRow_showErrorStrokeOnEditBox, true)

            inputType = a.getInt(R.styleable.EditableDataRow_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
            editBoxGravity = a.getInt(R.styleable.EditableDataRow_editBoxGravity, Gravity.CENTER)

            showBoxError = a.getBoolean(R.styleable.EditableDataRow_showBoxError, false)

            a.recycle()
        }

        // These should be done as last, at least after the allocation of the Views
        initBoxBackground()
        initEditBoxBackground()
        initError()

        clipChildren = false
        clipToPadding = false
    }

    private fun initEditBoxBackground() {
        editBoxShapeAppearanceModel = editBoxShapeAppearanceModel.toBuilder()
            .setTopLeftCornerSize(getCornerRadius(editBoxCornerRadiusTopStart, editBoxCornerRadius))
            .setTopRightCornerSize(getCornerRadius(editBoxCornerRadiusTopEnd, editBoxCornerRadius))
            .setBottomRightCornerSize(getCornerRadius(editBoxCornerRadiusBottomEnd, editBoxCornerRadius))
            .setBottomLeftCornerSize(getCornerRadius(editBoxCornerRadiusBottomStart, editBoxCornerRadius))
            .build()

        editBoxBackgroundDrawable = MaterialShapeDrawable(editBoxShapeAppearanceModel)

        editBoxBackgroundDrawable?.fillColor = editBoxBackgroundColor
        editBoxBackgroundDrawable?.setStroke(editBoxStrokeWidth, editBoxStrokeColor)

        etValue.setPadding(
            getPaddingOrFallback(editBoxPaddingContentHorizontal, editBoxPaddingContent),
            getPaddingOrFallback(editBoxPaddingContentVertical, editBoxPaddingContent),
            getPaddingOrFallback(editBoxPaddingContentHorizontal, editBoxPaddingContent),
            getPaddingOrFallback(editBoxPaddingContentVertical, editBoxPaddingContent)
        )

        etValue.background = editBoxBackgroundDrawable
        etValue.elevation = editBoxElevation
    }

    fun updateEditBoxCornerSizes(radius: Float = 0f) {
        updateCornerSizes(radius, radius, radius, radius)
    }

    fun updateEditBoxCornerSizes(topLeft: Float = 0f, topRight: Float = 0f, bottomRight: Float = 0f, bottomLeft: Float = 0f) {
        editBoxShapeAppearanceModel = editBoxShapeAppearanceModel.toBuilder()
            .setTopLeftCornerSize(topLeft)
            .setTopRightCornerSize(topRight)
            .setBottomRightCornerSize(bottomRight)
            .setBottomLeftCornerSize(bottomLeft)
            .build()
        editBoxBackgroundDrawable?.shapeAppearanceModel = editBoxShapeAppearanceModel
    }

    /**
     * First set the textAppearance for the value EditText.
     * If a specific color is specified override the color.
     */
    private fun updateValueEditTextAppearance() {
        if (valueTextAppearance != INVALID_INT) {
            TextViewCompat.setTextAppearance(etValue, valueTextAppearance)
        }
        valueTextColor?.let(etValue::setTextColor)
    }

    override fun setError(errorMessage: String) {
        super.setError(errorMessage)

        if (showErrorStrokeOnEditBox) {
            editBoxBackgroundDrawable?.setStroke(3f, editBoxStrokeErrorColor)
        }
    }

    override fun resetError() {
        super.resetError()
        editBoxBackgroundDrawable?.setStroke(editBoxStrokeWidth, editBoxStrokeErrorColor)
    }
}


// The 2-way data binding is creating an infinite loop
// https://stackoverflow.com/q/55559120/2263408
// Fixed it by using a real custom view instead of just a component and adding the necessary bindingAdapters to it
@BindingAdapter("valueText")
fun setValueText(view: EditableDataRow, unitFromValue: String?) {
    val oldVal = view.valueEditText.text?.toString() ?: ""
    if (oldVal == unitFromValue) return

    view.valueText = unitFromValue ?: ""
}

@InverseBindingAdapter(attribute = "valueText", event = "valueTextAttrChanged")
fun getValueText(view: EditableDataRow): String {
    return view.valueText.toString()
}

@BindingAdapter("valueTextAttrChanged")
fun valueTextChanged(view: EditableDataRow, listener: InverseBindingListener) {
    view.valueEditText.doOnTextChanged { text, _, _, _ ->
        val oldVal = view.valueText
        if (oldVal == text.toString()) return@doOnTextChanged

        view.valueText = text.toString()
        listener.onChange()
    }
}