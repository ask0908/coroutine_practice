package com.example.kotlinprac.paging.prac

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): RepoSearchResponse
}