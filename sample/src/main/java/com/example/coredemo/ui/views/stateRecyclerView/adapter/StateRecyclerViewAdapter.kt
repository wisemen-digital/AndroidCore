package com.example.coredemo.ui.views.stateRecyclerView.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coredemo.databinding.ListItemStateRecyclerviewBinding
import com.example.coredemo.ui.views.stateRecyclerView.StateRecyclerViewItem

class StateRecyclerViewAdapter : ListAdapter<StateRecyclerViewItem, StateRecyclerViewAdapter.StateRecyclerViewViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<StateRecyclerViewItem>() {
            override fun areItemsTheSame(oldItem: StateRecyclerViewItem, newItem: StateRecyclerViewItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StateRecyclerViewItem, newItem: StateRecyclerViewItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRecyclerViewViewHolder {
        return StateRecyclerViewViewHolder(ListItemStateRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StateRecyclerViewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StateRecyclerViewViewHolder(private val binding: ListItemStateRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StateRecyclerViewItem) {
            binding.item = item
        }
    }
}