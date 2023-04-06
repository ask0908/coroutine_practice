package com.example.kotlinprac.networkstate.two.data.model

import com.example.kotlinprac.networkstate.two.utils.CommonUtils
import com.google.gson.annotations.SerializedName

data class Source (
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)

data class ArticleResponse(
    @SerializedName("articles")
    var articles: MutableList<Article>? = null
)

data class Article(
    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var _publishedAt: String? = null,
    @SerializedName("content") var content: String? = null
) {
    val publishedAt
        get(): String? = CommonUtils().formatDate(_publishedAt.orEmpty())
}