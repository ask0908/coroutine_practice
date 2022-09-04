package com.example.kotlinprac.mapper.data.mappers

import com.example.kotlinprac.mapper.CharacterDetails
import com.example.kotlinprac.mapper.CharacterModel
import com.example.kotlinprac.mapper.data.responses.CharacterDataDto
import com.example.kotlinprac.mapper.data.responses.CharactersDto

/* mapper : 데이터 클래스를 한 구조에서 다른 구조로 변환하는 확장 기능
* DTO -> 도메인 모델 or 도메인 모델 -> DTO로 변환한다 */

// CharactersDTO -> CharacterModel
fun CharactersDto.toDomain() = CharacterModel(
    count = count,
    data = data.map { it.toDomain() }
)

fun CharacterDataDto.toDomain() = CharacterDetails(
    id = id,
    profileImageUrl = imageUrl,
    characterName = name,
)