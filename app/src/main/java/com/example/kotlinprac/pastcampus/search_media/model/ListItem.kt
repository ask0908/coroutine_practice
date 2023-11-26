package com.example.kotlinprac.pastcampus.search_media.model

import java.util.Date

// 2가지 타입의 뷰를 사용할 것
interface ListItem {
    val thumbnailUrl: String
    val dateTime: Date
    var isFavorite: Boolean
}