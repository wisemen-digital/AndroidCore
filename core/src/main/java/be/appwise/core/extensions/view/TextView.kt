package be.appwise.core.extensions.view

import android.widget.TextView
import androidx.core.text.HtmlCompat

fun TextView.setHtmlText(text: String?) {
    text?.let {
        this.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).trim()
    }
}
