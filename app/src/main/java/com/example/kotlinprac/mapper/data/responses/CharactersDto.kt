package com.example.kotlinprac.mapper.data.responses

// 데이터 클래스(DTO)는 기본적으로 직렬화 가능하다
data class CharactersDto(
    val count: Int,
    val `data`: List<CharacterDataDto>,
    val nextPage: String,
    val totalPages: Int
)

data class CharacterDataDto(
    var id: Int,
    val allies: List<Any>,
    val enemies: List<Any>,
    val films: List<Any>,
    val imageUrl: String,
    val name: String,
    val parkAttractions: List<Any>,
    val shortFilms: List<Any>,
    val tvShows: List<Any>,
    val url: String,
    val videoGames: List<Any>
)