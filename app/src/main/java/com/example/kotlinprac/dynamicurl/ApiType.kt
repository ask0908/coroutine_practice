package com.example.kotlinprac.dynamicurl

enum class ApiType(
    val url: String
) {
    DEV("dev.domain.com"),
    PRODUCT("domain.com"),
}