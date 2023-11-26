package com.example.kotlinprac.pastcampus.wallet.model

import java.util.Date

data class DetailItem(
    val id: Long,
    val date: Date,
    val content: String,
    val amount: Long, // 금액
    val type: Type
)

enum class Type {
    PAY,
    CANCEL,
    POINT
}
