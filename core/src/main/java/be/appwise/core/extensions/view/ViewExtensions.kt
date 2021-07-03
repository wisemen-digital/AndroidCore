package be.appwise.core.extensions.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.ViewGroup

/**
 * Set view visibility to View.GONE
 *
 * @param animated animates the visibility change
 * @param duration duration of the animation
 */
fun View.hide(animated: Boolean = false, duration: Long = 350L) {
    if(animated && visibility == View.VISIBLE){
        alpha = 1f
        animate()
            .alphaBy(-1f)
            .setDuration(duration)
            .withEndAction {
                visibility = View.GONE
            }
    } else {
        visibility = View.GONE
    }
}

/**
 * Set view visibility to View.INVISIBLE
 *
 * @param animated animates the visibility change
 * @param duration duration of the animation
 */
fun View.invisible(animated: Boolean = false, duration: Long = 350L) {
    if(animated && visibility == View.VISIBLE){
        alpha = 1f
        animate()
            .alphaBy(-1f)
            .setDuration(duration)
            .withEndAction {
                visibility = View.INVISIBLE
            }
    } else {
        visibility = View.INVISIBLE
    }
}

/**
 * Set view visibility to View.VISIBLE
 *
 * @param animated animates the visibility change
 * @param duration duration of the animation
 */
fun View.show(animated: Boolean = false, duration: Long = 350L) {
    if(animated && visibility != View.VISIBLE){
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alphaBy(1f).duration = duration
    } else {
        visibility = View.VISIBLE
    }
}

/**
 * @return Bitmap of the view and its children
 */
val View.asBitmap: Bitmap
    get() {
        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        measure(measureSpec, measureSpec)
        layout(0, 0, measuredWidth, measuredHeight)
        val output = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        output.eraseColor(Color.TRANSPARENT)
        val canvas = Canvas(output)
        draw(canvas)
        return output
    }

inline fun <reified T : ViewGroup.LayoutParams> View.getParams() = this.layoutParams as T

fun View.setOnClickListenerWithDisableDelay(delay: Long = 500, onItemClickListener: () -> Unit) {
    setOnClickListener {
        onItemClickListener()

        isEnabled = false
        Handler().postDelayed({
            isEnabled = true
        }, delay)
    }
}