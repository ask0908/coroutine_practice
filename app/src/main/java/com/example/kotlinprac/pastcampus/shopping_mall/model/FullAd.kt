package com.example.kotlinprac.pastcampus.shopping_mall.model

data class FullAd(
    val title: String,
    val imageUrl: String,
    val button: String? = null // 있을 수 있고 없을 수도 있음
): ListItem {
    override val viewType: ViewType
        get() = ViewType.FULL_AD

}
