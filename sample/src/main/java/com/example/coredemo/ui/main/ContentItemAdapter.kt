package com.example.coredemo.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coredemo.databinding.ListItemContentItemBinding
import com.example.coredemo.databinding.ListItemContentItemHeaderBinding

class ContentItemAdapter(val onClickListener: (contentItem: ContentItem) -> Unit) :
    ListAdapter<ContentItem, RecyclerView.ViewHolder>(ITEM_DIFF) {

    enum class Types(val id: Int) {
        ITEM(0),
        HEADER(1)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ContentItem>() {
            override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Types.ITEM.id -> ContentItemViewHolder(ListItemContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> ContentItemHeaderViewHolder(ListItemContentItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ContentItemViewHolder -> holder.bind(item as ContentItem)
            is ContentItemHeaderViewHolder -> holder.bind(item as ContentItem)
        }
    }

    inner class ContentItemViewHolder(private val binding: ListItemContentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            binding.contentItem = item
            binding.root.setOnClickListener { onClickListener(item) }
        }
    }

    inner class ContentItemHeaderViewHolder(private val binding: ListItemContentItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItem) {
            binding.contentItem = item
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).isSection) {
            true -> Types.HEADER.id
            else -> Types.ITEM.id
        }
    }
}