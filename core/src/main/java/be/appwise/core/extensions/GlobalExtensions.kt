package be.appwise.core.extensions

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

@Deprecated("This is not the desired way to show/style a Snackbar, theming is easier to do globally in the themes.xml")
internal fun snackBar(parentView: View, message: String, textColor: Int) =
    Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(context, textColor))
    }