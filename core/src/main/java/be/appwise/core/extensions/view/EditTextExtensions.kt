package be.appwise.core.extensions.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Now you can choose which callback you use, instead of being forced to implement all 3 of them.
 * For this to work just use the named parameters in the function.
 *
 * @param beforeTextChanged A function that will be called before the text changes
 * @param onTextChanged A function that will be called when the text changes
 * @param afterTextChanged A function that will be called after the text has changed
 */
fun EditText.optionalCallbacks(
    beforeTextChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    afterTextChanged: (s: String) -> Unit = {}) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s, start, before, count)
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged(editable.toString())
        }
    })
}