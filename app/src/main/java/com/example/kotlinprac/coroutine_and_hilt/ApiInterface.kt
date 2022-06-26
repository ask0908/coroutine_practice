package com.example.kotlinprac.coroutine_and_hilt

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/posts")
    suspend fun getPosts(): Response<String>
}