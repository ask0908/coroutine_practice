package com.example.kotlinprac.pastcampus.shopping_mall.model

class Empty: ListItem {
    override val viewType: ViewType
        get() = ViewType.EMPTY
}