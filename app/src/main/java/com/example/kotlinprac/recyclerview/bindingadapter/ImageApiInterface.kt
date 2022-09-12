package com.example.kotlinprac.recyclerview.bindingadapter

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiInterface {
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("client_id") clientId: String
    ): Response<List<ImageDTO>>
}