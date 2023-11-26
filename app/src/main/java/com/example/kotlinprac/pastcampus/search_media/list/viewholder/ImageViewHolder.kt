package com.example.kotlinprac.pastcampus.search_media.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemImageBinding
import com.example.kotlinprac.pastcampus.search_media.model.ImageItem
import com.example.kotlinprac.pastcampus.search_media.model.ListItem

class ImageViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem) {
        binding.item = item as ImageItem
    }
}