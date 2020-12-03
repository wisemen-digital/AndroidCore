package be.appwise.core.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import be.appwise.core.R
import be.appwise.core.extensions.view.optionalCallbacks
import kotlinx.android.synthetic.main.number_stepper.view.*

/**
 * A viewgroup containing a label [tvLabel] , minus button [ivMinus] , plus button [ivPlus] and a inputfield for a value [etValue]
 * @constructor Creates an [NumberStepper]
 *
 * You can change the minus button by adding ic_minus.xml under your drawables
 * You can change the plus button by adding ic_plus.xml under your drawables
 *
 * You can change the styling of [etValue] by adding a style with name numberStepperEdittext in your styles
*/

class NumberStepper : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.number_stepper, this, true)

        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.NumberStepper)
            setLabelText(attributes.getString(R.styleable.NumberStepper_label))
        }

        etValue.optionalCallbacks {
            mValueChangeListener?.onValueChanged(it.toInt())
        }

        ivPlus.setOnClickListener {
            val newValue = getValue() + 1
            etValue.setText((newValue).toString())
            mValueChangeListener?.onValueChanged(newValue)
        }

        ivMinus.setOnClickListener {
            val newValue = getValue() - 1
            etValue.setText((newValue).toString())
            mValueChangeListener?.onValueChanged(newValue)
        }
    }

    private var mValueChangeListener: ValueChangeListener? = null

    /**
     *  Gets triggered when users clicks on [ivPlus] and [ivMinus] or after text changes in [etValue]
     *  @see init
     * */
    interface ValueChangeListener {
        fun onValueChanged(value: Int)
    }


    /**
     * Sets a [valueChangeListener] for this [NumberStepper]
     * */
    fun setValueChangeListener(valueChangeListener: ValueChangeListener) {
        mValueChangeListener = valueChangeListener
    }

    /**
     * sets [label] as the text for [tvLabel] of this [NumberStepper]
     * when [label] is null or empty [tvLabel] and [spacing] will be hidden and only
     * */
    private fun setLabelText(label: String?) {
        tvLabel.text = label
        tvLabel.visibility = if (label.isNullOrEmpty()) View.GONE else View.VISIBLE
        spacing.visibility = if (label.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    /**
     * @return the text of [etValue] as an Int
     */
    fun getValue(): Int {
        val value =  etValue.text.toString()
        return if(value.isEmpty()) 0 else value.toInt()
    }

    /**
     * sets [number] as the text of [etValue]
     * @param number digit to show in the Numberstepper
     */
    fun setValueText(number: Int) {
        etValue.setText(number.toString())
    }
}