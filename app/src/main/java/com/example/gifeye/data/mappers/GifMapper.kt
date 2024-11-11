package com.example.gifeye.data.mappers

import com.example.gifeye.data.model.GifApiResponse
import com.example.gifeye.data.model.GifData
import com.example.gifeye.data.model.GifImages
import com.example.gifeye.data.model.GifImageDetail

object GifMapper {
    fun mapApiResponseToGifData(response: GifApiResponse): List<GifData> {
        return response.data.map { gif ->
            GifData(
                id = gif.id,
                title = gif.title,
                images = GifImages(
                    original = GifImageDetail(gif.images.original.url)
                )
            )
        }
    }
}