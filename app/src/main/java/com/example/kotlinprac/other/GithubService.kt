package com.example.kotlinprac.other

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String
    ): Response<GithubData>
}