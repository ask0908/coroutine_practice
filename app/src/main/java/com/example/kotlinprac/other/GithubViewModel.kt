package com.example.kotlinprac.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GithubViewModel(
    private val githubRepository: GithubRepository
): ViewModel() {
    var mGithubRepositories: MutableStateFlow<ApiState<GithubData>> = MutableStateFlow(ApiState.Loading())
    var githubRepositories: StateFlow<ApiState<GithubData>> = mGithubRepositories

    fun requestGithubRepositories(query: String) = viewModelScope.launch {
        mGithubRepositories.value = ApiState.Loading()
        githubRepository.getRepositories(query)
            .catch { error ->
                mGithubRepositories.value = ApiState.Error("${error.message}")
            }
            .collect { values ->
                mGithubRepositories.value = values
            }
    }

    fun test(query: String) = viewModelScope.launch {
        mGithubRepositories.value = ApiState.Loading()
        githubRepository.test(query)
            .catch { error ->
                mGithubRepositories.value = ApiState.Error("${error.message}")
            }
            .collect { values ->
                mGithubRepositories.value = values
            }
    }
}