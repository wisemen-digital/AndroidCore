package be.appwise.core.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun makeSnackbar(parentView: View, message: String, @ColorRes textColor: Int?) = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
    textColor?.let {
        setTextColor(ContextCompat.getColor(context, textColor))
    }
}