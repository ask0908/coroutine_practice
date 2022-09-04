package com.example.kotlinprac.mapper.data.remote

import com.example.kotlinprac.mapper.data.responses.CharactersDto
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun fetchCharacters(): CharactersDto
}