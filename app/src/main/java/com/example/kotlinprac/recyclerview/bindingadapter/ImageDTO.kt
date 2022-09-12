package com.example.kotlinprac.recyclerview.bindingadapter

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    val id: String,
    @SerializedName("urls") var imageUrls: ImageUrls
)

data class ImageUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val small_s3: String
)