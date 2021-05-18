package be.appwise.core.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import be.appwise.core.R
import be.appwise.core.databinding.ProfileActionButtonBinding
import be.appwise.core.databinding.ProfileActionButtonVerticalBinding

class ProfileActionButton @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private var mIsVertical: Boolean = false
    private var mTitle: String? = null
    private var mSubTitle: String? = null
    private var mHint: String? = null

    private var mDividerIsVisible: Boolean = false
    private var mNextIconIsVisible: Boolean = false
    private var mTitleTextAppearance: Int = -1
    private var mSubTitleTextAppearance: Int = -1
    private var mTitleTextColor: Int = -1
    private var mSubTitleTextColor: Int = -1
    private var mHintColor: Int = -1
    private var mDividerColor: Int = -1
    private var mNextIconColor: Int = -1
    private var mNextIcon: Drawable? = null

    private lateinit var mProfileActionButtonBinding: ViewBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        attributeSet?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProfileActionButton)
            mIsVertical = attributes.getBoolean(R.styleable.ProfileActionButton_pab_vertical, false)

            mTitle = attributes.getString(R.styleable.ProfileActionButton_pab_title)
            mSubTitle = attributes.getString(R.styleable.ProfileActionButton_pab_subTitle)
            mHint = attributes.getString(R.styleable.ProfileActionButton_pab_hint)

            mNextIconIsVisible =
                attributes.getBoolean(R.styleable.ProfileActionButton_pab_visibleNextIcon, true)
            mDividerIsVisible =
                attributes.getBoolean(R.styleable.ProfileActionButton_pab_visibleDivider, true)

            mTitleTextAppearance = attributes.getResourceId(
                R.styleable.ProfileActionButton_pab_titleTextAppearance,
                R.style.pab_title
            )
            mSubTitleTextAppearance = attributes.getResourceId(
                R.styleable.ProfileActionButton_pab_subTitleTextAppearance,
                R.style.pab_subTitle
            )

            mTitleTextColor = attributes.getColor(
                R.styleable.ProfileActionButton_pab_titleTextColor,
                android.R.color.black
            )
            mSubTitleTextColor = attributes.getColor(
                R.styleable.ProfileActionButton_pab_subTitleTextColor,
                android.R.color.black
            )
            mHintColor = attributes.getColor(
                R.styleable.ProfileActionButton_pab_hintTextColor,
                android.R.color.black
            )
            mNextIconColor = attributes.getColor(
                R.styleable.ProfileActionButton_pab_nextIconColor,
                android.R.color.black
            )
            mDividerColor = attributes.getColor(
                R.styleable.ProfileActionButton_pab_dividerColor,
                android.R.color.black
            )

            mNextIcon = attributes.getDrawable(R.styleable.ProfileActionButton_pab_nextIcon)
                ?: ContextCompat.getDrawable(
                    context.applicationContext,
                    R.drawable.ic_baseline_chevron_right
                )
            attributes.recycle()
        }
        mProfileActionButtonBinding = if (!mIsVertical) ProfileActionButtonBinding.inflate(
            inflater,
            this
        )
        else ProfileActionButtonVerticalBinding.inflate(
            inflater,
            this
        )

        isDividerVisible()
        isArrowVisible()
        setTitle(mTitle)
        setSubTitle(mSubTitle)
        setHint(mHint)


        titleView?.setTextAppearance(context, mTitleTextAppearance)

        subtitleView?.setTextAppearance(context, mTitleTextAppearance)
        titleView?.setTextColor(mTitleTextColor)
        subtitleView?.setTextColor(mSubTitleTextColor)
        subtitleView?.setHintTextColor(mHintColor)
        divider?.setBackgroundColor(mDividerColor)
        nextView?.setImageDrawable(mNextIcon)
        nextView?.imageTintList = ColorStateList.valueOf(mNextIconColor)

    }


    private val titleView get() = when (val binding = mProfileActionButtonBinding) {
        is ProfileActionButtonBinding -> binding.tvTitle
        is ProfileActionButtonVerticalBinding -> binding.tvTitle
        else -> null
    }

    private val subtitleView get() = when (val binding = mProfileActionButtonBinding) {
        is ProfileActionButtonBinding -> binding.tvSubtitle
        is ProfileActionButtonVerticalBinding -> binding.tvSubtitle
        else -> null
    }

    private val divider get() = when (val binding = mProfileActionButtonBinding) {
        is ProfileActionButtonBinding -> binding.divider
        is ProfileActionButtonVerticalBinding -> binding.divider
        else -> null
    }

    private val nextView get() = when (val binding = mProfileActionButtonBinding) {
        is ProfileActionButtonBinding -> binding.ivNext
        is ProfileActionButtonVerticalBinding -> binding.ivNext
        else -> null
    }


    private fun isDividerVisible() {
        divider?.isVisible = mDividerIsVisible
    }

    private fun isArrowVisible() {
        divider?.isVisible = mNextIconIsVisible
    }

    private fun setHint(hint: String?) {
        subtitleView?.isVisible = hint != null
        subtitleView?.hint = hint
    }

    fun setTitle(text: String?) {
        titleView?.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        /*spacing?.visibility = if (text.isNullOrEmpty()) View.VISIBLE else View.GONE*/
        titleView?.text = text
    }

    fun setSubTitle(text: String?) {
        subtitleView?.isVisible = text != null
        subtitleView?.text = text
    }
}