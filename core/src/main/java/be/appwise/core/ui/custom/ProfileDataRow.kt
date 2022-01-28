package be.appwise.core.ui.custom
import android.content.Context
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.annotation.ColorInt
import androidx.annotation.IntegerRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import be.appwise.core.R
import be.appwise.core.databinding.ProfileDataRowBinding
import org.joda.time.format.DateTimeFormat

class ProfileDataRow @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(ctx, attributeSet, defStyleAttr),TwoWayBindingManager {


    private var profileDataRowBinding : ProfileDataRowBinding

    private var mLabel : String? = null
    private var mValue : String? = null
    private var mValueHint : String? = null
    private var mLabelTextColor : Int
    private var mValueTextColor : Int
    private var mValueHintColor : Int
    private var mValueImeOptions : Int
    private var mValueInputType : Int
    private var mVisibleDivider : Boolean
    private var mDividerColor : Int
    private var mValueIsEditable : Boolean
    private var mValueIsSingleLine : Boolean

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.ProfileDataRow)
        mLabel = attributes.getString(R.styleable.ProfileDataRow_labelText)
        mValue = attributes.getString(R.styleable.ProfileDataRow_valueText)
        mValueHint = attributes.getString(R.styleable.ProfileDataRow_valueHint)
        mLabelTextColor = attributes.getColor(R.styleable.ProfileDataRow_labelTextColor,ContextCompat.getColor(context,android.R.color.black))
        mValueTextColor = attributes.getColor(R.styleable.ProfileDataRow_valueTextColor,ContextCompat.getColor(context,android.R.color.black))
        mValueHintColor = attributes.getColor(R.styleable.ProfileDataRow_valueHintColor,ContextCompat.getColor(context,android.R.color.black))
        mValueImeOptions = attributes.getInt(R.styleable.ProfileDataRow_android_imeOptions, EditorInfo.IME_ACTION_NEXT)
        mValueInputType = attributes.getInt(R.styleable.ProfileDataRow_android_inputType, EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES)
        mVisibleDivider = attributes.getBoolean(R.styleable.ProfileDataRow_visibleDivider,true)
        mDividerColor = attributes.getColor(R.styleable.ProfileDataRow_dividerColor,ContextCompat.getColor(context,android.R.color.black))
        mValueIsEditable = attributes.getBoolean(R.styleable.ProfileDataRow_editable,true)
        mValueIsSingleLine = attributes.getBoolean(R.styleable.ProfileDataRow_singleLine,true)

        attributes.recycle()

        profileDataRowBinding = ProfileDataRowBinding.inflate(inflater,this)

        setLabelText(mLabel)
        setValueText(mValue)
        setHint(mValueHint)

        setLabelTextColor(mLabelTextColor)
        setValueTextColor(mValueTextColor)
        profileDataRowBinding.etValue.imeOptions = mValueImeOptions
        profileDataRowBinding.etValue.inputType = mValueInputType
        setVisibleDivider(mVisibleDivider)
        setIsEditable(mValueIsEditable)

        profileDataRowBinding.etValue.isSingleLine = mValueIsSingleLine
        profileDataRowBinding.divider.setBackgroundColor(mDividerColor)

    }

    /*These setters can be called in code but also in databinding
    * didn't add them for imeoptions , inputtype */

    fun setLabelText(label : String?){
        profileDataRowBinding.tvLabel.text = label
    }

    fun setHint(hint : String?){
        profileDataRowBinding.etValue.hint = hint
    }

    fun setLabelTextColor(@IntegerRes color : Int?){
        color?.let {
            profileDataRowBinding.tvLabel.setTextColor(color)
        }
    }
    fun setValueTextColor(@IntegerRes color : Int?){
        color?.let {
            profileDataRowBinding.etValue.setTextColor(color)
        }
    }
    fun setVisibleDivider(isVisibleDivider : Boolean){
        profileDataRowBinding.divider.isVisible = isVisibleDivider

    }
    fun setIsEditable(editable : Boolean){
        profileDataRowBinding.etValue.isEnabled = editable
    }

    fun getValueText() = profileDataRowBinding.etValue.text.toString()

    fun setValueText(text: String?) {
        profileDataRowBinding.etValue.setText(text)
    }

    fun setValueText(date: Long) = profileDataRowBinding.etValue.setText(DateTimeFormat.shortDateTime().print(date.times(1000)))

    /*fun setValueDateText(date: Long) = etValue.setText(DateTimeFormat.shortDate().print(date.times(1000)))*/
    fun setValueDateText(date: Long) =
        profileDataRowBinding.etValue.setText(DateFormat.getDateFormat(context).format(date.times(1000)))

    override fun getEdittextForBinding(): AppCompatEditText {
        return profileDataRowBinding.etValue
    }

    fun setValueTextColor(@ColorInt color : Int){
        profileDataRowBinding.etValue.setTextColor(color)
    }


}