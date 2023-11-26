package com.example.kotlinprac.pastcampus.image_contract.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinprac.pastcampus.image_contract.mvvm.model.Image
import com.example.kotlinprac.pastcampus.image_contract.mvvm.repository.ImageRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MvvmViewModel(private val imageRepository: ImageRepository): ViewModel() {

    private val _countLiveData = MutableLiveData<String>()
    val countLiveData: LiveData<String> by lazy { _countLiveData }

    private val _imageLiveData = MutableLiveData<Image>()
    val imageLiveData: LiveData<Image> by lazy { _imageLiveData }

    private var disposable: CompositeDisposable? = CompositeDisposable()
    private var count = 0 // 이미지 개수

    fun loadRandomImage() {
        disposable?.add(imageRepository.getRandomImage()
            .doOnSuccess {
                count++
            }
            .subscribe { item ->
                _imageLiveData.value = item
                _countLiveData.value = "불러온 이미지 수 : $count"
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable = null
    }
}