package com.example.kotlinprac.paging.prac

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
): ViewModel() {
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val repoResult: LiveData<PagingData<Repo>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) {
            MutableLiveData(PagingData.empty())
        } else {
            repository.getSearchRepoResult(query).cachedIn(viewModelScope)
        }
    }

    val repos: LiveData<PagingData<Repo>> = repoResult

    fun searchRepos(query: String) {
        _searchQuery.value = query
    }
}