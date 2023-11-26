package com.example.kotlinprac.pastcampus.search_media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinprac.pastcampus.search_media.model.ListItem
import com.example.kotlinprac.pastcampus.search_media.repository.SearchRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {

    private val _listLiveData = MutableLiveData<List<ListItem>>()
    val listLiveData: LiveData<List<ListItem>>
        get() = _listLiveData

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun search(query: String) {
        disposable?.add(searchRepository.search(query)
            // repository가 시작될 때 로딩 표시
            .doOnSubscribe { _showLoading.value = true }
            // 데이터 가져오면 로딩 표시 제거
            .doOnTerminate { _showLoading.value = false }
            .subscribe({ list ->
                // 성공한 경우
                _listLiveData.value = list
            }, {
                // 실패한 경우
                _listLiveData.value = emptyList()
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }

    class SearchViewModelFactory(private val searchRepository: SearchRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(searchRepository) as T
        }
    }
}