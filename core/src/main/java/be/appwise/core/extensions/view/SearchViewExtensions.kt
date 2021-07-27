package be.appwise.core.extensions.view

import androidx.appcompat.widget.SearchView

/**
 *  Now you can choose which callback you use, instead of being forced to implement both of them.
 *  For this to work just use the named parameters in the function.
 *
 *  @param querySubmit Function with the query text that is to be submitted, the function expects a return value
 *  @return true if the query has been handled by the listener, false to let the
 *  SearchView perform the default action.
 *
 *  @param queryChange Funcation with the new content of the query text field, the function expects a return value
 *  @return false if the SearchView should perform the default action of showing any
 *  suggestions if available, true if the action was handled by the listener.
 */
fun SearchView.onQueryChange(querySubmit: (String) -> Boolean = { true }, queryChange: (String) -> Boolean = { true }) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return querySubmit(query)
        }

        override fun onQueryTextChange(query: String): Boolean {
            return queryChange(query)
        }
    })
}