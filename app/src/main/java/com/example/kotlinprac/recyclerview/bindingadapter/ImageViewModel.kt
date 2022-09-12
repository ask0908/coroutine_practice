package com.example.kotlinprac.recyclerview.bindingadapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val imageUseCase: ImageUseCase
): ViewModel() {
    var mImageResponse: MutableStateFlow<ApiState<List<ImageDTO>>> = MutableStateFlow(ApiState.Loading())
    val imageResponse: StateFlow<ApiState<List<ImageDTO>>> = mImageResponse

    fun getImages(page: Int, clientId: String) = viewModelScope.launch {
        mImageResponse.value = ApiState.Loading()
        imageUseCase.getImages(page, clientId)
            .catch { error ->
                mImageResponse.value = ApiState.Error("${error.message}")
            }
            .collect { values ->
                mImageResponse.value = values
            }
    }
}