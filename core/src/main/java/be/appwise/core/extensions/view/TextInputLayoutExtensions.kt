package be.appwise.core.extensions.view

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorLayout(error: String?) {
    isErrorEnabled = error != null
    setError(error)
}