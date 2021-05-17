package be.appwise.core.ui.custom

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import be.appwise.core.R
import be.appwise.core.databinding.NumberStepperBinding
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

class NumberStepper @JvmOverloads constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : LinearLayout(ctx, attributeSet, defStyleAttr) , TwoWayBindingManager {

    private lateinit var mNumberStepperBinding: NumberStepperBinding
    private var mMinValue: Double = Double.MIN_VALUE
    private var mMaxValue: Double = Double.MAX_VALUE

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mNumberStepperBinding = NumberStepperBinding.inflate(inflater, this)

        attributeSet?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.NumberStepper)
            setLabelText(attributes.getString(R.styleable.NumberStepper_label))

            //only set fields when set in xml --> needs double defaults instead of 0f
            if (attributes.hasValue(R.styleable.NumberStepper_minValue))
                mMinValue = attributes.getFloat(R.styleable.NumberStepper_minValue, 0f).toDouble()
            if (attributes.hasValue(R.styleable.NumberStepper_maxValue))
                mMaxValue =
                    attributes.getFloat(R.styleable.NumberStepper_maxValue, 0f).toDouble()

            with(mNumberStepperBinding) {
                ivPlus.setImageResource(
                    attributes.getResourceId(
                        R.styleable.NumberStepper_plus_icon,
                        R.drawable.ic_plus
                    )
                )
                ivMinus.setImageResource(
                    attributes.getResourceId(
                        R.styleable.NumberStepper_minus_icon,
                        R.drawable.ic_minus
                    )
                )
            }
        }

        with(mNumberStepperBinding) {


            val filter = InputFilter { source, start, end, dest, dstart, dend ->
                val futureText = dest.toString() + source.toString()
                if (futureText.toDouble() !in mMinValue..mMaxValue) "" else null
            }

            etValue.filters = arrayOf(filter)
            etValue.optionalCallbacks {
                mValueChangeListener?.onValueChanged(it.toInt())
            }

            ivPlus.setOnClickListener {
                val newValue = getValue() + 1
                if (newValue <= mMaxValue) {
                    etValue.setText((newValue).toString())
                    mValueChangeListener?.onValueChanged(newValue)
                }
            }

            ivMinus.setOnClickListener {
                val newValue = getValue() - 1
                if (newValue >= mMinValue) {
                    etValue.setText((newValue).toString())
                    mValueChangeListener?.onValueChanged(newValue)
                }
            }
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
        with(mNumberStepperBinding) {
            tvLabel.text = label
            tvLabel.visibility = if (label.isNullOrEmpty()) View.GONE else View.VISIBLE
            spacing.visibility = if (label.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    /**
     * @return the text of [etValue] as an Int
     */
    fun getValue(): Int {
        val value = mNumberStepperBinding.etValue.text.toString()
        return if (value.isEmpty()) 0 else value.toInt()
    }

    /**
     * sets [number] as the text of [etValue]
     * @param number digit to show in the Numberstepper
     */
    fun setValue(number: Int) {
        mNumberStepperBinding.etValue.setText(number.toString())
    }

    override fun getEdittextForBinding() = mNumberStepperBinding.etValue
}