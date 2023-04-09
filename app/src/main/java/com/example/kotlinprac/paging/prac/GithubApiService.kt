package com.example.kotlinprac.paging.prac

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int
    ): RepoSearchResponse
}