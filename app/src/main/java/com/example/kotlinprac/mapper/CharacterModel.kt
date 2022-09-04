package com.example.kotlinprac.mapper

data class CharacterModel(
    val count: Int?,
    val data:List<CharacterDetails>
)

// 도메인 모델
data class CharacterDetails(
    val id: Int?,
    val profileImageUrl: String?,
    val characterName: String?,
)