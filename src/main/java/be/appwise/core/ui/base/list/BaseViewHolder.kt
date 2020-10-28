package be.appwise.core.ui.base.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var context: Context = itemView.context
    abstract fun bind(item: T)
}