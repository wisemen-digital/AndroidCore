package be.appwise.core.extensions.fragment

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import be.appwise.core.extensions.snackBar

@Deprecated("This is not the desired way to show/style a Snackbar, theming is easier to do globally in the themes.xml")
fun Fragment.snackBar(message: String, textColor: Int = android.R.color.white, viewID: Int = android.R.id.content) {
    activity ?: return
    snackBar(activity!!.findViewById(viewID), message, textColor).show()
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