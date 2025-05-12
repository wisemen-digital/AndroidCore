package be.appwise.core.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import be.appwise.core.R
import be.appwise.core.extensions.fragment.showSnackBar

open class BaseDialogFragment: DialogFragment() {
    lateinit var parentActivity: AppCompatActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as AppCompatActivity
    }

    open fun onError(throwable: Throwable) {
        val message = throwable.message ?: getString(R.string.error_default)
        showSnackBar(message)
        Log.e("BaseDialogfragment", message, throwable)
    }
}
