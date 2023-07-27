package com.example.kotlinprac.rx

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxGithubRepository @Inject constructor(
    private val githubApi: RxGithubApi
) {
    fun getRepositories(user: String): Observable<List<Repository>> =
        githubApi.getRepositories(user)

    fun getRepositoryNames(user: String): Observable<List<String>> =
        githubApi.getRepositories(user)
            .map { repositories -> repositories.map { it.name } }
}