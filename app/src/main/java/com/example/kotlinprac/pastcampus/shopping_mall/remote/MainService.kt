package com.example.kotlinprac.pastcampus.shopping_mall.remote

import com.example.kotlinprac.pastcampus.shopping_mall.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("chapter6")
    suspend fun getList(
        @Query("page") page: Int,
        @Query("size") size: Int = 20,
    ): NetworkResponse
}