package com.example.kotlinprac.permission

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PermissionRequestViewModel @Inject constructor() : ViewModel() {

    // 권한 상태를 나타내는 StateFlow
    private var _permissionState = MutableStateFlow(false)
    val permissionState: StateFlow<Boolean> = _permissionState

    /**
     * 권한 허용 여부를 T/F로 갖는 StateFlow를 업데이트하는 함수
     *
     * @param isAllowed 권한 허용 여부
     */
    fun updatePermissionState(isAllowed: Boolean) {
        _permissionState.value = isAllowed
    }
}