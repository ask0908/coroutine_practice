package com.example.kotlinprac.paging.prac

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val service: GithubApiService
) {
    fun getSearchRepoResult(query: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 50 // 스크롤이 200개 남았을 때 새 데이터를 불러옴. 화면에 표시되는 개수의 몇 배로 설정해야 함
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }
}