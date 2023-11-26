package com.example.kotlinprac.pastcampus.image_contract.mvvm.repository

import com.example.kotlinprac.pastcampus.image_contract.RetrofitManager
import com.example.kotlinprac.pastcampus.image_contract.mvvm.model.Image
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ImageRepositoryImpl: ImageRepository {
    override fun getRandomImage(): Single<Image> = RetrofitManager.imageService.getRandomImageRx()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { item ->
            Single.just(Image(item.urls.regular, item.color))
        }
}