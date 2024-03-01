package be.appwise.datarow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter


@Suppress("MemberVisibilityCanBePrivate")
class DataRow @JvmOverloads constructor(
    ctx: Context, attributeSet: AttributeSet? = null,
    // you could also pass in "0" for "defStyleAttr" if you don't need any default values or anything
    defStyleAttr: Int = R.attr.dataRowStyle
) : BaseDataRow(ctx, attributeSet, defStyleAttr) {

    companion object {
        private val DEF_STYLE_RES = R.style.Widget_Core_DataRow
    }

    // <editor-fold desc="Views">
    private var tvContent: TextView
    private var ivArrow: ImageView
    // </editor-fold>

    /**
     * Controls whether the end Arrow should be shown or not
     */
    var showEndDrawable: Boolean = false
        set(value) {
            field = value
            updateArrowVisibility()
        }

    var endDrawable: Drawable? = null
        set(value) {
            field = value
            ivArrow.setImageDrawable(value)
        }

    var endDrawableTint: ColorStateList? = null
        set(value) {
            field = value
            if (value != null) ivArrow.imageTintList = value
        }

    var valueTextMaxLines: Int = 1
        set(value) {
            field = value
            tvContent.maxLines = value
        }

    // <editor-fold desc="Texts">
    var valueText: String? = ""
        set(value) {
            field = value
            updateContentTextView()
        }
    var hintText: String? = ""
        set(value) {
            field = value
            updateContentTextView()
        }

    // </editor-fold>

    // <editor-fold desc="TextAppearances">
    @StyleRes
    var hintTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateTextAppearanceContentTextView()
        }

    @StyleRes
    var valueTextAppearance: Int = INVALID_INT
        set(value) {
            field = value
            updateTextAppearanceContentTextView()
        }
    // </editor-fold>

    // <editor-fold desc="TextColor">
    var hintTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateTextAppearanceContentTextView()
        }

    var valueTextColor: ColorStateList? = null
        set(value) {
            field = value
            updateTextAppearanceContentTextView()
        }
    // </editor-fold>

    init {
        inflate(context, R.layout.data_row, this)

        tvLabel = findViewById(R.id.tvLabel)
        tvContent = findViewById(R.id.tvContent)
        tvSubtitle = findViewById(R.id.tvSubtitle)
        ivArrow = findViewById(R.id.ivArrow)
        clData = findViewById(R.id.clData)
        tvError = findViewById(R.id.tvError)

        // get all attributes that would be needed
        context.obtainStyledAttributes(
            attributeSet, R.styleable.DataRow, R.attr.dataRowStyle, // Could be "0" if this would not be used
            DEF_STYLE_RES
        ).let { a ->

            // <editor-fold desc="Get all attributes">
            showEndDrawable = a.getBoolean(R.styleable.DataRow_showEndDrawable, false)
            endDrawable = a.getDrawable(R.styleable.DataRow_endDrawable) ?: ContextCompat.getDrawable(context, R.drawable.ic_arrow_right)
            endDrawableTint = a.getColorStateList(R.styleable.DataRow_endDrawableTint)
            valueText = a.getString(R.styleable.DataRow_valueText) ?: ""
            hintText = a.getString(R.styleable.DataRow_hintText) ?: ""
            valueTextMaxLines = a.getInt(R.styleable.DataRow_valueTextMaxLines, 1)
            // </editor-fold>

            // <editor-fold desc="TextAppearances">
            hintTextAppearance = a.getResourceId(R.styleable.DataRow_hintTextAppearance, INVALID_INT)
            valueTextAppearance = a.getResourceId(R.styleable.DataRow_valueTextAppearance, INVALID_INT)
            // </editor-fold>

            // <editor-fold desc="TextColors">

            // Getting the colors should be done after the TextAppearance,
            // this is to make sure a specific color takes precedence before an Appearance
            hintTextColor = a.getColorStateList(R.styleable.DataRow_hintTextColor)
            valueTextColor = a.getColorStateList(R.styleable.DataRow_valueTextColor)
            // </editor-fold>

            showBoxError = a.getBoolean(R.styleable.DataRow_showBoxError, false)

            a.recycle()
        }

        // These should be done as last, at least after the allocation of the Views
        initBoxBackground()
        initError()

        clipChildren = false
        clipToPadding = false
    }

    private fun updateArrowVisibility() {
        ivArrow.visibility = if (showEndDrawable) VISIBLE else GONE
    }

    /**
     * When updating the [tvContent] we will first check if a [valueText] or [hintText] has ben set.
     * In case both are not set we will not be showing this view.
     *
     * After the first check, we'll see if a [valueText] has been set.
     * If the [valueText] has been set, we will update the [tvContent]
     * with the data and update the TextAppearance accordingly.
     */
    private fun updateContentTextView() {
        if (valueText.isNullOrEmpty() && hintText.isNullOrEmpty()) {
            tvContent.visibility = GONE
            return
        }

        tvContent.visibility = VISIBLE
        tvContent.text = if (!valueText.isNullOrEmpty()) valueText else hintText

        /**
         * An update of the TextAppearance is needed here because there will probably
         * be a difference between the [valueText] and the [hintText] textAppearances
         */
        updateTextAppearanceContentTextView()
    }

    private fun updateTextAppearanceContentTextView() {
        if (valueText.isNullOrEmpty()) {
            updateTextViewAppearance(tvContent, hintTextColor, hintTextAppearance)
        } else {
            updateTextViewAppearance(tvContent, valueTextColor, valueTextAppearance)
        }
    }
}

// This one is needed so we can also support databinding a String Resource to the labelText
@BindingAdapter("labelText")
fun setText(view: DataRow, @StringRes labelText: Int) {
    val text = view.context.getString(labelText)

    val oldVal = view.labelText
    if (oldVal == text) return

    view.labelText = text
}

//TODO: still to be tested on every DataRow!!!
@BindingAdapter("app:labelText")
fun setText(view: BaseDataRow, text: CharSequence?) {
    val textView = view.findViewById<TextView>(R.id.tvLabel)
    val oldText = textView.text
    if (text === oldText || text == null && oldText.isEmpty()) {
        return
    }
    if (text is Spanned) {
        if (text == oldText) {
            return  // No change in the spans, so don't set anything.
        }
    }
    textView.text = text
}