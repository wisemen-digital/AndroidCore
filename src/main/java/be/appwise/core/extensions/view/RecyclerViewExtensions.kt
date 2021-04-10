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

/**
 * Sets up the RecyclerView with some common used parameters.
 *
 * @param decoration A decoration item that should be used between each listItem, defaults to the DividerItemDecoration.
 *                   In case the decoration is 'null' no decoration will be added.
 * @param layoutManager The layoutManager to be used with the RecyclerView, defaults to the LinearLayoutManager
 * @param hasFixedSize True if adapter changes cannot affect the size of the RecyclerView
 */
fun RecyclerView.setupRecyclerView(
    decoration: RecyclerView.ItemDecoration? = DividerItemDecoration(context, DividerItemDecoration.VERTICAL),
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    hasFixedSize: Boolean = true
) {
    setHasFixedSize(hasFixedSize)
    this.layoutManager = layoutManager

    if (decoration != null) {
        addItemDecoration(decoration)
    }
}
