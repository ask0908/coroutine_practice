package com.example.kotlinprac.coroutine_and_hilt.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.kotlinprac.coroutine_and_hilt.ApiState
import com.example.kotlinprac.coroutine_and_hilt.usecase.ApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiUseCase: ApiUseCase
): BaseViewModel() {

    private val TAG = this.javaClass.simpleName

    fun getPosts() = viewModelScope.launch {
        mResponse.value = ApiState.Loading()
        apiUseCase.getPosts()
            .catch { error ->
                mResponse.value = ApiState.Error("${error.message}")
            }
            .collect { values ->
                mResponse.value = values
            }
    }

}