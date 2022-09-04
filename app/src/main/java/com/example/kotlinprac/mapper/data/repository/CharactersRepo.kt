package com.example.kotlinprac.mapper.data.repository

import com.example.kotlinprac.mapper.CharacterModel
import com.example.kotlinprac.mapper.data.mappers.toDomain
import com.example.kotlinprac.mapper.data.remote.ApiService
import com.example.kotlinprac.mapper.data.responses.ApiResource
import com.example.kotlinprac.mapper.data.responses.safeApiCall
import javax.inject.Inject

interface CharactersRepo {
    suspend fun getCharacters(): ApiResource<CharacterModel>
}

// repository 인터페이스의 구현이기 때문에 이 함수의 사용처는 뷰모델밖에 없다
class CharactersRepoImpl @Inject constructor(
    private val apiService: ApiService
): CharactersRepo {
    // CharactersDto를 리턴하는 함수 뒤에 toDomain()을 붙여서 리턴값을 CharacterModel 클래스로 변환
    override suspend fun getCharacters() = safeApiCall { apiService.fetchCharacters().toDomain() }
}