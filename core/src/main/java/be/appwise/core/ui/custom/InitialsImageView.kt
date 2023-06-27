package be.appwise.core.ui.custom


import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import be.appwise.core.R
import be.appwise.core.extensions.libraries.loadCircle
import be.appwise.core.extensions.libraries.loadFileCircle
//import kotlinx.android.synthetic.main.initials_imageview.view.*
import java.io.File

class InitialsImageView : RelativeLayout {


    fun init(attrs: AttributeSet? = null) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.initials_imageview, this, true)

        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.InitialsImageView)
            with(attributes) {
                val color = getResourceId(R.styleable.InitialsImageView_circleColor, android.R.color.black)
                setCircleColor(color)

                val textSize = getDimensionPixelSize(R.styleable.InitialsImageView_android_textSize, -1)
                findViewById<TextView>(R.id.tvInitials).setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize.toFloat())

                val textColor = getResourceId(R.styleable.InitialsImageView_android_textColor, android.R.color.white)
                setTextColor(textColor)


                recycle()
            }
        }
    }

    fun setCircleColor(color : Int){
        findViewById<ImageView>(R.id.ivInitialCircle).setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }

    fun setTextColor(color : Int){
        findViewById<TextView>(R.id.tvInitials).setTextColor(ContextCompat.getColor(context,color))
    }
    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }


    interface Profile {
        fun getImage(): String?
        fun getInitials(): String
    }

    fun setProfile(profile: Profile?) {
        profile?.let {
            setInitials(it.getInitials())
            loadPicture(it.getImage())
        }
    }

    private fun setInitials(initials: String) {
        findViewById<TextView>(R.id.tvInitials).text = initials
    }

    fun loadPicture(url: String?) {
        findViewById<ImageView>(R.id.ivPicture).loadCircle(url)
    }

    fun loadFile(file : File){
        findViewById<ImageView>(R.id.ivPicture).loadFileCircle(file)
    }
}