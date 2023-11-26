package com.example.kotlinprac.pastcampus.image_contract.mvi

sealed class MviIntent {
    object LoadImage: MviIntent()
}
