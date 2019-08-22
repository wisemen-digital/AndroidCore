package be.appwise.core.extensions.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDivider(drawable: Int, orientation: Int = LinearLayoutManager.VERTICAL) {
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    dividerItemDecoration.setDrawable(
        ContextCompat.getDrawable(
            context,
            drawable
        )!!
    )
    addItemDecoration(dividerItemDecoration)
}