package be.appwise.core.extensions.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import be.appwise.core.extensions.makeSnackbar

@Deprecated("This is not the desired way to show/style a Snackbar, theming is easier to do globally in the themes.xml (by changing 'surfaceColor'). Use 'showSnackbar(message, view, textColor)' for better results")
fun Activity.snackbar(message: String, textColor: Int = android.R.color.white, viewID: Int = android.R.id.content) =
    showSnackbar(message, findViewById(viewID), textColor)

fun Activity.showSnackbar(message: String, view: View = findViewById(android.R.id.content), @ColorRes textColor: Int? = null) =
    makeSnackbar(message, view, textColor).show()

fun Activity.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.allPermissionsGranted(permissions: ArrayList<String>): Boolean {
    // Will map over each permission and check if it was granted
    // Only distinct values will be kept.
    return permissions.map {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }.distinct().contains(false)
}

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