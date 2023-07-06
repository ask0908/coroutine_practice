package com.example.kotlinprac.paging.prac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val service: GithubApiService
) {
    fun getSearchRepoResult(query: String): LiveData<PagingData<Repo>> {
        if (query.isBlank()) {
            return MutableLiveData<PagingData<Repo>>(PagingData.empty()).asFlow().asLiveData()
        }
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 200
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow.asLiveData()
    }
}