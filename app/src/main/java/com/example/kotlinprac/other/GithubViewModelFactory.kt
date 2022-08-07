package com.example.kotlinprac.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GithubViewModelFactory(
    private val githubRepository: GithubRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GithubRepository::class.java).newInstance(githubRepository)
    }
}