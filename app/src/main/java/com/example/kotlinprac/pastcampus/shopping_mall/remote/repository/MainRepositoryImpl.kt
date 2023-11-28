package com.example.kotlinprac.pastcampus.shopping_mall.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.remote.MainPagingSource
import com.example.kotlinprac.pastcampus.shopping_mall.remote.MainService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService
): MainRepository {
    override fun loadList(): Flow<PagingData<ListItem>> = Pager(
        config = PagingConfig(
            pageSize = 20, // 첫 호출 시 pagingSize의 3배만큼 호출함. 이후 여기 정의된 개수만큼 불러옴
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MainPagingSource(mainService)
        }
    ).flow
}