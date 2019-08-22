package be.appwise.core.extensions.view

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun SearchView.onQueryChange(queryChange: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String): Boolean {
            //niks doen voorlopig
            return true;
        }

        override fun onQueryTextChange(query: String): Boolean {
            queryChange(query)
            return true
        }
    })
}