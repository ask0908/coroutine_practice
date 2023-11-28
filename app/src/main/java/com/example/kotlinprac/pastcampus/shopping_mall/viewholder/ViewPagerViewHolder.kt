package com.example.kotlinprac.pastcampus.shopping_mall.viewholder

import com.example.kotlinprac.databinding.ItemViewpagerBinding
import com.example.kotlinprac.pastcampus.shopping_mall.ListAdapter
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.model.ViewPager

class ViewPagerViewHolder(
    binding: ItemViewpagerBinding
): BindingViewHolder<ItemViewpagerBinding>(binding) {
    private val adapter = ListAdapter()

    init {
        binding.viewpager.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as ViewPager
        adapter.submitList(item.items)
    }
}