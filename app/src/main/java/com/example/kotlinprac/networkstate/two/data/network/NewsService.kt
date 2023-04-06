package com.example.kotlinprac.networkstate.two.data.network

import com.example.kotlinprac.networkstate.two.data.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines?country=us")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ArticleResponse>

    @GET("everything?domains=techcrunch.com&sortBy=popularity")
    suspend fun getNewsList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ArticleResponse>
}