package com.example.kotlinprac.pastcampus.search_media

import com.example.kotlinprac.pastcampus.search_media.model.ImageListResponse
import com.example.kotlinprac.pastcampus.search_media.model.VideoListResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {
    @Headers("Authorization: KakaoAK 4b43ce35c5790a801bf35fa843f1c1b9")
    @GET("image")
    fun searchImage(
        @Query("query") query: String
    ): Observable<ImageListResponse>

    @Headers("Authorization: KakaoAK 4b43ce35c5790a801bf35fa843f1c1b9")
    @GET("vclip")
    fun searchVideo(
        @Query("query") query: String
    ): Observable<VideoListResponse>
}