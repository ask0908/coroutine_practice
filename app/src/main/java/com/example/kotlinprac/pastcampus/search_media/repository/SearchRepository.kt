package com.example.kotlinprac.pastcampus.search_media.repository

import com.example.kotlinprac.pastcampus.search_media.model.ListItem
import io.reactivex.rxjava3.core.Observable

interface SearchRepository {
    fun search(query: String): Observable<List<ListItem>>
}