package com.example.kotlinprac.recyclerview.stickyheader

/* 헤더에 넣을 아이템. 헤더에 들어갈 문자열, 헤더에 붙을지 여부, 헤더 밑에 표시할 아이템을 data class로 받는다 */
data class StickyItem(
    val country: String,
    val normalItem: NormalItem,
    val isSticky: Boolean
)
