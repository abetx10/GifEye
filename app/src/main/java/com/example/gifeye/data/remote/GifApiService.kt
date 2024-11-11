package com.example.gifeye.data.remote

import com.example.gifeye.data.model.GifApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApiService {

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") lang: String
    ): GifApiResponse
}