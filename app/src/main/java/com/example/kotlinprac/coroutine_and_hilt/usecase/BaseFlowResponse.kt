package com.example.kotlinprac.coroutine_and_hilt.usecase

import com.example.kotlinprac.coroutine_and_hilt.ApiState
import retrofit2.Response
import java.io.IOException

/* Flow 사용 시 서버 응답을 받을 때 필요한 공통 코드를 정의한 클래스 */
abstract class BaseFlowResponse {
    suspend fun <T> safeFlowCall(apiCall: suspend () -> Response<T>): ApiState<String> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                response.body()?.let {
                    return ApiState.Success(body.toString())
                }
            } else {
                try {
                    return ApiState.Error(response.errorBody()!!.string())
                }   catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return printError("${response.code()} : ${response.errorBody()?.string()}")
        }   catch (e: Exception) {
            return ApiState.Error(e.message ?: "")
        }
    }

    private fun printError(errorMessage: String): ApiState<String> = ApiState.Error("BaseFlowResponse - API 호출 실패 : $errorMessage")

}