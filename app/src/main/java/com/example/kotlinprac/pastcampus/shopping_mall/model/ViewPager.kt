package com.example.kotlinprac.pastcampus.shopping_mall.model

data class ViewPager(
    val items: List<ListItem>
): ListItem {
    override val viewType: ViewType
        get() = ViewType.VIEW_PAGER
}
