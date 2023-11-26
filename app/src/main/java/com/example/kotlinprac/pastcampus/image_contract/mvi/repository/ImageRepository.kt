package com.example.kotlinprac.pastcampus.image_contract.mvi.repository

import com.example.kotlinprac.pastcampus.image_contract.mvi.model.Image

interface ImageRepository {
    suspend fun getRandomImage(): Image
}