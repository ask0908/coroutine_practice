package com.example.kotlinprac.pastcampus.search_media.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemVideoBinding
import com.example.kotlinprac.pastcampus.search_media.list.ItemHandler
import com.example.kotlinprac.pastcampus.search_media.model.ListItem
import com.example.kotlinprac.pastcampus.search_media.model.VideoItem

class VideoViewHolder(
    private val binding: ItemVideoBinding,
    private val itemHandler: ItemHandler? = null // searchFragment에서만 액션을 넣고, favoriteFragment에선 넣지 않을 거라 null로 기본값 대입
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem) {
        binding.item = item as VideoItem
        binding.handler = itemHandler
    }
}