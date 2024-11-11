package com.example.gifeye.domain.usecase

import com.example.gifeye.data.model.GifData
import com.example.gifeye.data.repository.GifRepository
import javax.inject.Inject

class SearchGifsUseCase @Inject constructor(private val repository: GifRepository) {
    suspend operator fun invoke(query: String, limit: Int, offset: Int): List<GifData> {
        return repository.searchGifs(query, limit, offset)
    }
}