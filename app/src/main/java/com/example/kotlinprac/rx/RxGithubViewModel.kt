package com.example.kotlinprac.rx

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RxGithubViewModel @Inject constructor(
    private val repository: RxGithubRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun getRepositories(user: String): Observable<List<Repository>> =
        repository.getRepositories(user)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { disposable.add(it) }

    fun getRepositoryNames(user: String): Observable<List<String>> =
        repository.getRepositoryNames(user)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe{ disposable.add(it) }

    override fun onCleared() {
        super.onCleared()
        // 뷰모델이 사라질 때 모든 disposable을 제거 -> 메모리 누수 방지
        disposable.clear()
    }
}