package be.appwise.core.extensions.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds a divider between every listItem, when no parameters are given it will draw a default fine line horizontally in a vertical list.
 * Using named parameters you can override one or both of them.
 *
 * @param drawable Specific drawable to be used as a divider, default is a fine light gray line
 * @param orientation The orientation of the list, default is a Vertical list thus a horizontal line
 */
fun RecyclerView.addDivider(drawable: Int? = null, orientation: Int = LinearLayoutManager.VERTICAL) {
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    if (drawable != null) {
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, drawable)!!)
    }
    addItemDecoration(dividerItemDecoration)
}

fun RecyclerView.setupRecyclerView(decoration: DividerItemDecoration? = DividerItemDecoration(context, DividerItemDecoration.VERTICAL), layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) =
    apply {
        setHasFixedSize(true)
        this.layoutManager = layoutManager

        if (decoration != null) {
            addItemDecoration(decoration)
        }
    }
