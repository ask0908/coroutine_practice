package com.example.kotlinprac.pastcampus.shopping_mall.model

data class Image(
    val imageUrl: String
): ListItem {
    override val viewType: ViewType
        get() = ViewType.IMAGE
}
