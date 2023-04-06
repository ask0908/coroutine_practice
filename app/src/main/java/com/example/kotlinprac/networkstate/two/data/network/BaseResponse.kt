package com.example.kotlinprac.networkstate.two.data.network

import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return ApiResponse.success(it)
                }
            }
            return ApiResponse.error(HttpException(response))
        } catch (e: Exception) {
            return ApiResponse.error(e)
        }
    }
}