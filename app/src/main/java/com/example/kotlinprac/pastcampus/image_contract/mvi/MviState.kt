package com.example.kotlinprac.pastcampus.image_contract.mvi

import com.example.kotlinprac.pastcampus.image_contract.mvi.model.Image

sealed class MviState {
    object Idle: MviState() // 초기값
    object Loading: MviState() // 로딩바 표시
    // sealed class 안에서 인자가 있다면 data class로 받고 인자가 없으면 object로 받는다
    data class LoadedImage(val image: Image, val count: Int): MviState()
}
