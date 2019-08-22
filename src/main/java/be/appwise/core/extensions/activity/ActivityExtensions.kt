package be.appwise.core.extensions.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresPermission

fun Activity.snackBar(message: String, textColor: Int = android.R.color.white, viewID: Int = android.R.id.content) =
    be.appwise.core.extensions.snackBar(findViewById(viewID), message, textColor).show()

//keyboard management
fun Activity.openKeyBoard(edittext: EditText) {
    edittext.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.hideKeyboard() {
    // Check if no view has focus:
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isLollipopOrMore(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun Activity.getDeviceWidth() = with(this) {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics.widthPixels
}

fun Activity.getDeviceHeight() = with(this) {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics.heightPixels
}

/**
 * Returns if the app is currently connected to the internet.
 *
 * @return true if connected to the internet
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}