package com.example.kotlinprac.pastcampus.shopping_mall

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.viewholder.BindingViewHolder
import com.example.kotlinprac.pastcampus.shopping_mall.viewholder.ViewHolderGenerator

class ListAdapter: ListAdapter<ListItem, BindingViewHolder<*>>(DiffUtilCallback()) {

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