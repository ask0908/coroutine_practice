package com.example.kotlinprac.pastcampus.wallet

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemDetailBinding
import com.example.kotlinprac.pastcampus.wallet.model.DetailItem

class DetailViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DetailItem) {
        binding.item = item
    }
}