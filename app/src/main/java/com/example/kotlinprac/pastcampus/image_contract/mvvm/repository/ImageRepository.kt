package com.example.kotlinprac.pastcampus.image_contract.mvvm.repository

import com.example.kotlinprac.pastcampus.image_contract.mvvm.model.Image
import io.reactivex.rxjava3.core.Single

interface ImageRepository {
    fun getRandomImage(): Single<Image>
}