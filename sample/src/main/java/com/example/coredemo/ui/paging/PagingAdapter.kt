package com.example.coredemo.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import be.appwise.core.ui.base.list.BaseViewHolder
import com.example.coredemo.data.database.entity.Spell
import com.example.coredemo.databinding.ListItemPagingBinding

class PagingAdapter : PagingDataAdapter<Spell, PagingAdapter.PagingViewHolder>(PAGING_DIFF) {

    companion object {
        private val PAGING_DIFF = object : DiffUtil.ItemCallback<Spell>() {
            override fun areItemsTheSame(oldItem: Spell, newItem: Spell): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Spell, newItem: Spell): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(ListItemPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PagingViewHolder(private val binding: ListItemPagingBinding) : BaseViewHolder<Spell>(binding.root) {
        override fun bind(item: Spell) {
            binding.item = item
        }
    }
}