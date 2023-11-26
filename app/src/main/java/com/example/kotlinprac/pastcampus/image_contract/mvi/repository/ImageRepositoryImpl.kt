package com.example.kotlinprac.pastcampus.image_contract.mvi.repository

import com.example.kotlinprac.pastcampus.image_contract.RetrofitManager
import com.example.kotlinprac.pastcampus.image_contract.mvi.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 디스패처를 초기화 시 불러와서 함수 호출 시 넣는다
// 테스트 작성 시 초기화에 디스패처를 넣어야 쉽게 테스트 작성할 수 있음
class ImageRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ImageRepository {
    override suspend fun getRandomImage(): Image = withContext(dispatcher) {
        RetrofitManager.imageService.getRandomImageSuspend().let {
            Image(it.urls.regular, it.color)
        }
    }
}