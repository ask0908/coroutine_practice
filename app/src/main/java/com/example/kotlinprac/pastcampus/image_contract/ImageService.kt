package com.example.kotlinprac.pastcampus.image_contract

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageService {
    @Headers("Authorization: Client-ID exaamTZ6k0zbst2JhhAidmJYAU66-Y1VRjclyeC1Gpc")
    @GET("/photos/random")
    fun getRandomImageRx(): Single<ImageResponse>

    // MviImageContractActivity에서 사용
    @Headers("Authorization: Client-ID exaamTZ6k0zbst2JhhAidmJYAU66-Y1VRjclyeC1Gpc")
    @GET("/photos/random")
    suspend fun getRandomImageSuspend(): ImageResponse
}