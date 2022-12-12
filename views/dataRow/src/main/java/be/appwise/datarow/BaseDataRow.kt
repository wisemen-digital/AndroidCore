package be.appwise.datarow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
open class BaseDataRow @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    // you could also pass in "0" for "defStyleAttr" if you don't need any default values or anything
    defStyleAttr: Int = R.attr.dataRowStyle
) : LinearLayout(ctx, attributeSet, defStyleAttr) {

    companion object {
        private val ENABLED_PRESSED_STATE_SET = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed)

        private const val TRANSPARENT_DEFAULT_COLOR_WARNING = ("Use a non-transparent color for the default color as it will be used to finish ripple animations.")

        private val DEF_STYLE_RES = R.style.Widget_Core_BaseDataRow

        const val INVALID_FLOAT = -1f
        const val INVALID_INT = -1
    }

    // <editor-fold desc="Views">
    protected var tvLabel: TextView? = null
        set(value) {
            field = value
            value?.text = labelText
            updateTextViewAppearance(value, labelTextColor, labelTextAppearance)
        }
    protected var tvSubtitle: TextView? = null
        set(value) {
            field = value
            updateSubtitleTextView()
            updateTextViewAppearance(value, subtitleTextColor, subtitleTextAppearance)
        }
    protected var clData: ConstraintLayout? = null
    protected var tvError: TextView? = null
        set(value) {
            field = value
            value?.text = errorText
            updateTextViewAppearance(value, errorTextColor, errorTextAppearance)
        }
    // </editor-fold>

    private var shapeAppearanceModel: ShapeAppearanceModel
    private var boxBackgroundDrawable: MaterialShapeDrawable? = null
    protected var paddingContent: Float = 0f
    protected var paddingContentHorizontal: Float = 0f
    protected var paddingContentVertical: Float = 0f
    private var boxElevation: Float = 0f

    // <editor-fold desc="Texts">
    /**
     * Sets a label text that will be displayed as the first
     * text to let the user know what this [DataRow] is about.
     *
     * Keeping it short is key. For longer information the [subtitleText] can be used.
     */
    var labelText: String? = ""
        set(value) {
            field = value
            tvLabel?.text = value
        }
    var subtitleText: String? = ""
        set(value) {
            field = value
            updateSubtitleTextView()
        }
    var errorText: String? = ""
        set(value) {
            field = value
            tvError?.text = value
        }
    // </editor-fold>

    // <editor-fold desc="TextAppearances">
    @StyleRes
    var labelTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateTextViewAppearance(tvLabel, labelTextColor, value)
        }

    @StyleRes
    var subtitleTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateTextViewAppearance(tvSubtitle, subtitleTextColor, value)
        }

    @StyleRes
    var errorTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateTextViewAppearance(tvError, errorTextColor, value)
        }
    // </editor-fold>

    // <editor-fold desc="TextColor">
    var labelTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateTextViewAppearance(tvLabel, value, labelTextAppearance)
        }

    var subtitleTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateTextViewAppearance(tvSubtitle, value, subtitleTextAppearance)
        }

    var errorTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateTextViewAppearance(tvError, value, errorTextAppearance)
        }
    // </editor-fold>

    private var rippleColor: ColorStateList? = null
    private var boxBackgroundColor: ColorStateList? = null
    private var boxStrokeColor: ColorStateList? = null
    private var boxStrokeErrorColor: ColorStateList? = null
    private var boxStrokeWidth: Float = 0f

    var boxCornerRadius: Float = 0f
        set(value) {
            field = value
            updateCornerSizes(value)
        }
    var boxCornerRadiusTopStart: Float = 0f
        set(value) {
            field = value
            updateCornerSizes(topLeft = value)
        }
    var boxCornerRadiusTopEnd: Float = 0f
        set(value) {
            field = value
            updateCornerSizes(topRight = value)
        }
    var boxCornerRadiusBottomEnd: Float = 0f
        set(value) {
            field = value
            updateCornerSizes(bottomRight = value)
        }
    var boxCornerRadiusBottomStart: Float = 0f
        set(value) {
            field = value
            updateCornerSizes(bottomLeft = value)
        }
    var showBoxError: Boolean = false

    init {
        // get all attributes that would be needed
        context.obtainStyledAttributes(
            attributeSet,
            R.styleable.BaseDataRow,
            defStyleAttr,
            DEF_STYLE_RES
        ).let { a ->

            // <editor-fold desc="Get all attributes">
            labelText = a.getString(R.styleable.BaseDataRow_labelText) ?: ""
            subtitleText = a.getString(R.styleable.BaseDataRow_subtitleText) ?: ""

            // <editor-fold desc="TextAppearances">
            labelTextAppearance = a.getResourceId(R.styleable.BaseDataRow_labelTextAppearance, INVALID_INT)
            subtitleTextAppearance = a.getResourceId(R.styleable.BaseDataRow_subtitleTextAppearance, INVALID_INT)
            errorTextAppearance = a.getResourceId(R.styleable.BaseDataRow_errorTextAppearance, INVALID_INT)
            // </editor-fold>

            // <editor-fold desc="TextColors">

            // Getting the colors should be done after the TextAppearance,
            // this is to make sure a specific color takes precedence before an Appearance
            labelTextColor = a.getColorStateList(R.styleable.BaseDataRow_labelTextColor)
            subtitleTextColor = a.getColorStateList(R.styleable.BaseDataRow_subtitleTextColor)
            errorTextColor = a.getColorStateList(R.styleable.BaseDataRow_errorTextColor)
            // </editor-fold>

            boxStrokeErrorColor = a.getColorStateList(R.styleable.BaseDataRow_boxStrokeErrorColor)
            boxStrokeColor = a.getColorStateList(R.styleable.BaseDataRow_boxStrokeColor)

            paddingContent = a.getDimension(R.styleable.BaseDataRow_paddingContent, 0f)
            paddingContentHorizontal = a.getDimension(R.styleable.BaseDataRow_paddingContentHorizontal, INVALID_FLOAT)
            paddingContentVertical = a.getDimension(R.styleable.BaseDataRow_paddingContentVertical, INVALID_FLOAT)
            boxElevation = a.getDimension(R.styleable.BaseDataRow_boxElevation, 0f)
            boxStrokeWidth = a.getDimension(R.styleable.BaseDataRow_boxStrokeWidth, 0f)

            // <editor-fold desc="Background">
            shapeAppearanceModel = ShapeAppearanceModel.builder(context, attributeSet, defStyleAttr, DEF_STYLE_RES).build()
            boxCornerRadius = a.getDimension(R.styleable.BaseDataRow_boxCornerRadius, INVALID_FLOAT)
            boxCornerRadiusTopStart = a.getDimension(R.styleable.BaseDataRow_boxCornerRadiusTopStart, INVALID_FLOAT)
            boxCornerRadiusTopEnd = a.getDimension(R.styleable.BaseDataRow_boxCornerRadiusTopEnd, INVALID_FLOAT)
            boxCornerRadiusBottomEnd = a.getDimension(R.styleable.BaseDataRow_boxCornerRadiusBottomEnd, INVALID_FLOAT)
            boxCornerRadiusBottomStart = a.getDimension(R.styleable.BaseDataRow_boxCornerRadiusBottomStart, INVALID_FLOAT)
            // </editor-fold>

            rippleColor = a.getColorStateList(R.styleable.BaseDataRow_rippleColor)
            boxBackgroundColor = a.getColorStateList(R.styleable.BaseDataRow_boxBackgroundColor)

            showBoxError = a.getBoolean(R.styleable.BaseDataRow_showBoxError, false)

            a.recycle()
        }
    }

    protected fun initBoxBackground() {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setTopLeftCornerSize(getCornerRadius(boxCornerRadiusTopStart, boxCornerRadius))
            .setTopRightCornerSize(getCornerRadius(boxCornerRadiusTopEnd, boxCornerRadius))
            .setBottomRightCornerSize(getCornerRadius(boxCornerRadiusBottomEnd, boxCornerRadius))
            .setBottomLeftCornerSize(getCornerRadius(boxCornerRadiusBottomStart, boxCornerRadius))
            .build()

        boxBackgroundDrawable = MaterialShapeDrawable(shapeAppearanceModel)

        boxBackgroundDrawable?.fillColor = boxBackgroundColor
        boxBackgroundDrawable?.setStroke(boxStrokeWidth, boxStrokeColor)

        val someBackground = RippleDrawable(sanitizeRippleDrawableColor(rippleColor), boxBackgroundDrawable, null)

        clData?.background = someBackground
        clData?.elevation = boxElevation

        clData?.setPadding(
            getPaddingOrFallback(paddingContentHorizontal, paddingContent),
            getPaddingOrFallback(paddingContentVertical, paddingContent),
            getPaddingOrFallback(paddingContentHorizontal, paddingContent),
            getPaddingOrFallback(paddingContentVertical, paddingContent)
        )
    }

    /**
     * Returns a [ColorStateList] that is safe to pass to [ ].
     *
     *
     * If given a null ColorStateList, this will return a new transparent ColorStateList since
     * RippleDrawable requires a non-null ColorStateList.
     *
     *
     * If given a non-null ColorStateList, this method will log a warning for API 22-27 if the
     * ColorStateList is transparent in the default state and non-transparent in the pressed state.
     * This will result in using the pressed state color for the ripple until the finger is lifted at
     * which point the ripple will transition to the default state color (transparent), making the
     * ripple appear to terminate prematurely.
     */
    protected fun sanitizeRippleDrawableColor(rippleColor: ColorStateList?): ColorStateList {
        if (rippleColor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1 && Color.alpha(rippleColor.defaultColor) == 0 && (Color.alpha(
                    rippleColor.getColorForState(
                        ENABLED_PRESSED_STATE_SET,
                        Color.TRANSPARENT
                    )
                )
                        != 0)
            ) {
                Log.w("BaseDataRow Things", TRANSPARENT_DEFAULT_COLOR_WARNING)
            }
            return rippleColor
        }
        return ColorStateList.valueOf(Color.TRANSPARENT)
    }

    private fun updateSubtitleTextView() {
        tvSubtitle?.visibility = if (subtitleText.isNullOrEmpty()) GONE else VISIBLE
        tvSubtitle?.text = subtitleText
    }

    protected fun updateTextViewAppearance(tv: TextView?, textColor: ColorStateList?, appearance: Int) {
        setTextAppearanceOnTextView(tv, appearance)
        setTextColorOnTextView(tv, textColor)
    }

    protected fun setTextColorOnTextView(tv: TextView?, textColor: ColorStateList?) {
        if (textColor != null && tv != null) {
            tv.setTextColor(textColor)
        }
    }

    protected fun setTextAppearanceOnTextView(tv: TextView?, appearance: Int) {
        if (appearance != INVALID_INT && tv != null) {
            TextViewCompat.setTextAppearance(tv, appearance)
        }
    }

    protected fun getPaddingOrFallback(padding: Float, fallbackPadding: Float): Int {
        return (if (padding > INVALID_FLOAT) padding else fallbackPadding).toInt()
    }

    protected fun getCornerRadius(specificRadius: Float, generalRadius: Float): Float {
        return if (specificRadius >= 0) specificRadius else generalRadius
    }

    fun updateCornerSizes(radius: Float = 0f) {
        updateCornerSizes(radius, radius, radius, radius)
    }

    fun updateCornerSizes(topLeft: Float = 0f, topRight: Float = 0f, bottomRight: Float = 0f, bottomLeft: Float = 0f) {
        shapeAppearanceModel = shapeAppearanceModel.toBuilder()
            .setTopLeftCornerSize(topLeft)
            .setTopRightCornerSize(topRight)
            .setBottomRightCornerSize(bottomRight)
            .setBottomLeftCornerSize(bottomLeft)
            .build()
        boxBackgroundDrawable?.shapeAppearanceModel = shapeAppearanceModel
    }

    protected fun initError() {
        setTextColorOnTextView(tvError, errorTextColor)
        tvError?.setPadding(
            getPaddingOrFallback(paddingContentHorizontal, paddingContent), 0,
            getPaddingOrFallback(paddingContentHorizontal, paddingContent), 0
        )

    }

    open fun setError(errorMessage: String) {
        tvError?.text = errorMessage
        tvError?.visibility = View.VISIBLE
        if (showBoxError) {
            boxBackgroundDrawable?.setStroke(3f, boxStrokeErrorColor)
        }
    }

    open fun resetError() {
        tvError?.text = ""
        tvError?.visibility = View.GONE
        boxBackgroundDrawable?.setStroke(boxStrokeWidth, boxStrokeColor)
    }
}