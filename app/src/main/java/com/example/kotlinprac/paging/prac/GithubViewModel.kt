package com.example.kotlinprac.paging.prac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
): ViewModel() {
    private val _searchQuery = MutableStateFlow<String>("")

    val repoResult: Flow<PagingData<Repo>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.getSearchRepoResult(query).cachedIn(viewModelScope)
            }
        }

    fun searchRepos(query: String) {
        _searchQuery.value = query
    }
}