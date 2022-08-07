package com.example.kotlinprac.other

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class GithubRepository: BaseFlowResponse() {
    private val githubClient = ApiService.client

    suspend fun getRepositories(queryString: String): Flow<ApiState<GithubData>> = flow {
        emit(flowCall { githubClient.getRepositories(queryString) })
    }.flowOn(Dispatchers.IO)

    suspend fun test(query: String): Flow<ApiState<GithubData>> = flow {
        try {
            val response = githubClient.getRepositories(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                }
            } else {
                try {
                    emit(ApiState.Error(response.errorBody()!!.string()))
                }   catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }   catch (e: Exception) {
            emit(ApiState.Error(e.message ?: ""))
        } as Unit
    }.flowOn(Dispatchers.IO)
}