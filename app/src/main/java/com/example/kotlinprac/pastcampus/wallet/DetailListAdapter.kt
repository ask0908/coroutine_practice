package com.example.kotlinprac.pastcampus.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.kotlinprac.databinding.ItemDetailBinding
import com.example.kotlinprac.pastcampus.wallet.model.DetailItem

class DetailListAdapter: ListAdapter<DetailItem, DetailViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback: DiffUtil.ItemCallback<DetailItem>() {
        override fun areItemsTheSame(oldItem: DetailItem, newItem: DetailItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DetailItem, newItem: DetailItem): Boolean =
            oldItem == newItem
    }
}