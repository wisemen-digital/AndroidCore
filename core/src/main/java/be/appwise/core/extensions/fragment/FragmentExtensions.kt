package be.appwise.core.extensions.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import be.appwise.core.extensions.makeSnackbar

@Deprecated("This is not the desired way to show/style a Snackbar, theming is easier to do globally in the themes.xml (by changing 'surfaceColor'). Use 'showSnackbar(message, view, textColor)' for better results")
fun Fragment.snackBar(message: String, textColor: Int = android.R.color.white, viewID: Int = android.R.id.content) {
    showSnackBar(message, requireView(), textColor)
}

fun Fragment.showSnackBar(message: String, view: View = requireView(), @ColorRes textColor: Int? = null) {
    makeSnackbar(view, message, textColor).show()
}

fun Fragment.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
}
@Suppress("SimplifyBooleanWithConstants")
fun Fragment.allPermissionsGranted(permissions: ArrayList<String>): Boolean {
    // Will map over each permission and check if it was granted
    // Only distinct values will be kept.
    return permissions.map {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }.all { it == true } // This check actually needs to happen cannot be simplified to just `true`
}

fun Fragment.openKeyBoard(editText: EditText) {
    activity ?: return

    editText.requestFocus()
    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {
    activity ?: return

    // Check if no view has focus:
    val view = activity!!.currentFocus
    if (view != null) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.replaceFragment(fragment: Fragment, addToBackstack: Boolean, TAG: String, id: Int) {
    val fragmentManager = childFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(id, fragment, TAG)
    if (addToBackstack) {
        fragmentTransaction.addToBackStack(TAG)
    }
    try {
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}