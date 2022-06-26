package com.example.kotlinprac.coroutine_and_hilt.usecase

import com.example.kotlinprac.coroutine_and_hilt.ApiInterface
import com.example.kotlinprac.coroutine_and_hilt.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiUseCase @Inject constructor(
    private val apiInterface: ApiInterface
): BaseFlowResponse() {
    suspend fun getPosts(): Flow<ApiState<String>> = flow {
        emit (safeFlowCall { apiInterface.getPosts() })
    }.flowOn(Dispatchers.IO)
}