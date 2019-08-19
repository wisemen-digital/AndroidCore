package be.appwise.core.extensions.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


/**
 * Callback after text is changed
 * @param afterTextChanged callback (s: String) -> Unit
 */
fun EditText.afterTextChanged(afterTextChanged: (s: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged(editable.toString())
        }
    })
}