package com.example.kotlinprac.pastcampus.shopping_mall

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.viewholder.BindingViewHolder
import com.example.kotlinprac.pastcampus.shopping_mall.viewholder.ViewHolderGenerator

// ViewHolderGenerator만 잘 들어가면 어떤 리스트를 구현해도 동일한 구현체를 받는다
class PagingListAdapter: PagingDataAdapter<ListItem, BindingViewHolder<*>>(DiffUtilCallback()) {
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        // -1을 받으면 ViewHolderGenerator.get()의 else로 빠짐
        return item?.viewType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return ViewHolderGenerator.get(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}