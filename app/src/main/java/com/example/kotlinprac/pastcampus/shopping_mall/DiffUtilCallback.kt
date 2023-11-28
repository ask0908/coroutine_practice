package com.example.kotlinprac.pastcampus.shopping_mall

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem

class DiffUtilCallback<T: ListItem>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.viewType == newItem.viewType && oldItem.getKey() == newItem.getKey()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}