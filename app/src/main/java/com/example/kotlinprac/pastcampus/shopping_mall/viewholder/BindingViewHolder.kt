package com.example.kotlinprac.pastcampus.shopping_mall.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.BR
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem

abstract class BindingViewHolder<VB: ViewDataBinding>(
    private val binding: VB
): RecyclerView.ViewHolder(binding.root) {
    // 아이템이 없더라도 사용할 수 있게 함
    protected var item: ListItem? = null

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
    }
}