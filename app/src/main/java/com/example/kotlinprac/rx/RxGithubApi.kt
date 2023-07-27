package com.example.kotlinprac.rx

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RxGithubApi {
    @GET("users/{user}/repos")
    fun getRepositories(
        @Path("user") user: String
    ): Observable<List<Repository>>
}