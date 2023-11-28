package com.example.kotlinprac.pastcampus.shopping_mall.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.model.SampleMock

class MainPagingSource(private val mainService: MainService) : PagingSource<Int, ListItem>() {
    // 사용 안함
    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int = 0

    //    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
//        return try {
//            val page = params.key ?: 1
//            val size = params.loadSize
//            val result = mainService.getList(page, size).data
//
//            LoadResult.Page(
//                data = result.list,
//                prevKey = null,
//                nextKey = result.page.nextPage
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return try {
            val page = params.key ?: 1
            val size = params.loadSize
            val result = SampleMock.mockChapter6List()
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}