package com.example.kotlinprac.pastcampus.shopping_mall.viewholder

import com.example.kotlinprac.databinding.ItemHorizontalBinding
import com.example.kotlinprac.pastcampus.shopping_mall.ListAdapter
import com.example.kotlinprac.pastcampus.shopping_mall.model.Horizontal
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem

class HorizontalViewHolder(
    private val binding: ItemHorizontalBinding
): BindingViewHolder<ItemHorizontalBinding>(binding) {
    private val adapter = ListAdapter()

    init {
        binding.listView.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal
        binding.titleTextView.text = item.title
        adapter.submitList(item.items)
    }
}