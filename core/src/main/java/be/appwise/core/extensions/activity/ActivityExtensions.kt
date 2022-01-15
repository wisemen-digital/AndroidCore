package be.appwise.core.extensions.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.widget.EditText
import androidx.annotation.RequiresPermission
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import be.appwise.core.extensions.snackBar

@Deprecated("This is not the desired way to show/style a Snackbar, theming is easier to do globally in the themes.xml")
fun Activity.snackBar(message: String, textColor: Int = android.R.color.white, viewID: Int = android.R.id.content) =
    snackBar(findViewById(viewID), message, textColor).show()

//keyboard management
fun Activity.openKeyBoard(edittext: EditText) {
    WindowInsetsControllerCompat(window, edittext).show(WindowInsetsCompat.Type.ime())
}

fun Activity.hideKeyboard() {
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())
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