package com.example.kotlinprac.pastcampus.search_media.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemVideoBinding
import com.example.kotlinprac.pastcampus.search_media.model.ListItem
import com.example.kotlinprac.pastcampus.search_media.model.VideoItem

class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem) {
        binding.item = item as VideoItem
    }
}