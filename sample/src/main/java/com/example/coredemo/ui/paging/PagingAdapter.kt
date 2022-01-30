package com.example.coredemo.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import be.appwise.core.ui.base.list.BaseViewHolder
import com.example.coredemo.databinding.ListItemPagingBinding

class PagingAdapter : PagingDataAdapter<String, PagingAdapter.PagingViewHolder>(PAGING_DIFF) {

    companion object {
        private val PAGING_DIFF = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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

    inner class PagingViewHolder(private val binding: ListItemPagingBinding) : BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) {

        }
    }
}