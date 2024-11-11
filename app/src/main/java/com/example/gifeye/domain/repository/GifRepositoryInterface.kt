package com.example.gifeye.domain.repository

import com.example.gifeye.data.model.GifData

interface GifRepositoryInterface {
    suspend fun searchGifs(query: String, limit: Int, offset: Int): List<GifData>
    suspend fun deleteGif(id: String)
}