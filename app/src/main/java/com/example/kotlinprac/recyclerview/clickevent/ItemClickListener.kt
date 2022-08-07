package com.example.kotlinprac.recyclerview.clickevent

interface ItemClickListener<T> {
    fun onItemClick(pos: Int, item: T)
}