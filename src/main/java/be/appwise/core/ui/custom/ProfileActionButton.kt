package be.appwise.core.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import be.appwise.core.R
import kotlinx.android.synthetic.main.profile_action_button.view.*

class ProfileActionButton : LinearLayout {

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

    private var mIsVertical: Boolean = false


    private fun init(context: Context, attrs: AttributeSet? = null) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProfileActionButton)
            mIsVertical = attributes.getBoolean(R.styleable.ProfileActionButton_pab_vertical, false)
            inflater.inflate(
                if (mIsVertical) R.layout.profile_action_button_vertical else R.layout.profile_action_button,
                this,
                true
            )


            for (index in 0 until attributes.indexCount) {
                val attr = attributes.getIndex(index)
                when (attr) {
                    R.styleable.ProfileActionButton_pab_title -> {
                        setTitle(attributes.getString(attr))
                    }
                    R.styleable.ProfileActionButton_pab_subTitle -> {
                        setSubTitle(attributes.getString(attr))
                    }
                    R.styleable.ProfileActionButton_pab_hint -> {
                        setHint(attributes.getString(attr))
                    }
                    R.styleable.ProfileActionButton_pab_visibleArrow -> {
                        when (attributes.getBoolean(attr, true)) {
                            true -> ivNext.visibility = View.VISIBLE
                            false -> ivNext.visibility = View.GONE
                        }
                    }
                    R.styleable.ProfileActionButton_pab_visibleDivider -> {
                        when (attributes.getBoolean(attr, true)) {
                            true -> divider.visibility = View.VISIBLE
                            false -> divider.visibility = View.GONE
                        }
                    }
                    R.styleable.ProfileActionButton_pab_textColor -> {
                        tvTitle.setTextColor(
                            attributes.getColor(
                                attr,
                                ContextCompat.getColor(context, android.R.color.white)
                            )
                        )
                        tvSubtitle.setTextColor(
                            attributes.getColor(
                                attr,
                                ContextCompat.getColor(context, android.R.color.white)
                            )
                        )
                    }

                    R.styleable.ProfileActionButton_pab_titleTextAppearance -> tvTitle.setTextAppearance(attributes.getResourceId(attr, -1))

                    R.styleable.ProfileActionButton_pab_titleTextColor -> {
                        tvTitle.setTextColor(attributes.getColor(attr, android.R.color.black))
                    }
                    R.styleable.ProfileActionButton_pab_subTitleTextColor -> {
                        tvSubtitle.setTextColor(attributes.getColor(attr, android.R.color.black))
                    }
                    R.styleable.ProfileActionButton_pab_dividerColor -> {
                        divider.setBackgroundColor(attributes.getColor(attr, android.R.color.black))
                    }
                    R.styleable.ProfileActionButton_pab_arrowColor -> {
                        ivNext.imageTintList =
                            ColorStateList.valueOf(attributes.getColor(attr, android.R.color.black))
                    }


                }
            }
            attributes.recycle()

        }
    }

    private fun setHint(hint: String?) {
        tvSubtitle.hint = hint
    }

    fun setTitle(text: String?) {
        tvTitle.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        /*spacing?.visibility = if (text.isNullOrEmpty()) View.VISIBLE else View.GONE*/
        tvTitle.text = text
    }

    fun setSubTitle(text: String?) {
        tvSubtitle.isVisible = text != null
        tvSubtitle.text = text
    }
}