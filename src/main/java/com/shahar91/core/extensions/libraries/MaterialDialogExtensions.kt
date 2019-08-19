package com.shahar91.core.extensions.libraries

import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import com.shahar91.core.R

fun Activity.getInformation(
    titleText: Int = R.string.app_name, contentText: Int,
    action: () -> Unit = {}
): MaterialDialog {
    return MaterialDialog(this).show {
        title(titleText)
        message(contentText)
        positiveButton {
            action()
        }
    }
}

fun Activity.getDualDialog(
    title: Int,
    content: Int,
    positiveAction: () -> Unit?,
    negativeAction: () -> Unit?
): MaterialDialog {
    return MaterialDialog(this).show {
        title(title)
        message(content)
        positiveButton {
            positiveAction()
        }
        negativeButton {
            negativeAction()
        }
    }
}