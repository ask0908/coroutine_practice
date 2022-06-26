package com.example.kotlinprac.coroutine_and_hilt.viewmodels

import androidx.lifecycle.ViewModel
import com.example.kotlinprac.coroutine_and_hilt.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel: ViewModel() {
    var mResponse: MutableStateFlow<ApiState<String>> = MutableStateFlow(ApiState.Loading())
    var response: StateFlow<ApiState<String>> = mResponse
}