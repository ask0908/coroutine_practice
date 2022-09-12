package com.example.kotlinprac.recyclerview.bindingadapter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageUseCase @Inject constructor(
    private val imageApiInterface: ImageApiInterface
): BaseFlowResponse() {
    suspend fun getImages(page: Int, clientId: String): Flow<ApiState<List<ImageDTO>>> = flow {
        emit(safeObjectFlowCall { imageApiInterface.getImages(page, clientId) })
    }.flowOn(Dispatchers.IO)
}