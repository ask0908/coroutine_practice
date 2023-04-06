package com.example.kotlinprac.networkstate.two.data.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val commonService: NewsService
) : BaseDataSource() {
    suspend fun getTopHeadlines(page: Int, pageSize: Int) = getResult {
        commonService.getTopHeadlines(page, pageSize)
    }

    suspend fun getNewsList(page: Int, pageSize: Int) = getResult {
        commonService.getNewsList(page, pageSize)
    }

}