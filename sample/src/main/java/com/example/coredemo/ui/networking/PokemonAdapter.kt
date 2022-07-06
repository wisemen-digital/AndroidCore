package com.example.coredemo.ui.networking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import be.appwise.core.ui.base.list.BaseViewHolder
import com.example.coredemo.databinding.ListItemPokemonBinding

class PokemonAdapter(val onClickListener: (pokemon: PokemonResponse) -> Unit) : ListAdapter<PokemonResponse, PokemonAdapter.PokemonViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<PokemonResponse>() {
            override fun areItemsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(ListItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(private val binding: ListItemPokemonBinding) : BaseViewHolder<PokemonResponse>(binding.root) {
        override fun bind(item: PokemonResponse) {
            binding.item = item

            binding.root.setOnClickListener {
                onClickListener(item)
            }
        }
    }
}