package com.example.gifeye.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GifApiResponse(
    val data: List<GifData>
)

@Parcelize
data class GifData(
    val id: String,
    val title: String,
    val images: GifImages
) : Parcelable

@Parcelize
data class GifImages(
    val original: GifImageDetail
) : Parcelable

@Parcelize
data class GifImageDetail(
    val url: String
) : Parcelable