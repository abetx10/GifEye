package com.example.gifeye.domain.usecase

import com.example.gifeye.domain.repository.GifRepositoryInterface
import javax.inject.Inject

class DeleteGifUseCase @Inject constructor(private val repository: GifRepositoryInterface) {
    suspend operator fun invoke(id: String) {
        repository.deleteGif(id)
    }
}