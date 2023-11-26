package com.example.kotlinprac.pastcampus.search_media.list

import com.example.kotlinprac.pastcampus.search_media.model.ListItem

interface ItemHandler {
    fun onClickFavorite(item: ListItem)
}