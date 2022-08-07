package com.example.kotlinprac.other

import retrofit2.Response
import java.io.IOException

abstract class BaseFlowResponse {
    suspend fun <T> flowCall(apiCall: suspend () -> Response<T>): ApiState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    return ApiState.Success(it)
                }
            } else {
                try {
                    return ApiState.Error(response.errorBody()!!.string())
                }   catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return ApiState.Error("")
        }   catch (e: Exception) {
            return ApiState.Error(e.message ?: "")
        }
    }
}