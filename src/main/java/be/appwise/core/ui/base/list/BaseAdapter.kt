package be.appwise.core.ui.base.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, L, VH : BaseViewHolder<T, L>>(context: Context, private val listener: L) :
    RecyclerView.Adapter<VH>() {

    private val items: MutableList<T> = ArrayList()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.bind(position, item, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): List<T> {
        return items
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun addItem(item: T?) {
        if (item == null) {
            throw IllegalArgumentException("Argument cannot be null")
        }
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(items: List<T>?) {
        requireNotNull(items) { "Argument cannot be null" }
        this.items.addAll(items)
        notifyItemRangeInserted(this.items.size - items.size, items.size)
    }

    fun addItems(items: List<T>?, positionStart: Int) {
        requireNotNull(items) { "Argument cannot be null" }
        this.items.addAll(positionStart, items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        val position = items.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(itemOld: T, itemNew: T) {
        require(!(itemOld == null || itemNew == null)) { "Argument cannot be null" }
        val position = items.indexOf(itemOld)
        items[position] = itemNew
        notifyItemChanged(position, itemNew)
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    protected fun inflate(@LayoutRes layout: Int, parent: ViewGroup,
        attachToRoot: Boolean = false): View {
        return layoutInflater.inflate(layout, parent, attachToRoot)
    }
}