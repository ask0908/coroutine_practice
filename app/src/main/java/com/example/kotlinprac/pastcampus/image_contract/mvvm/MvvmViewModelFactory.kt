package com.example.kotlinprac.pastcampus.image_contract.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinprac.pastcampus.image_contract.mvvm.repository.ImageRepository

class MvvmViewModelFactory(private val imageRepository: ImageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MvvmViewModel(imageRepository) as T
    }
}