package com.example.kotlinprac.pastcampus.shopping_mall.remote.repository

import androidx.paging.PagingData
import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun loadList(): Flow<PagingData<ListItem>>
}