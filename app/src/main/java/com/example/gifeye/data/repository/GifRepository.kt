package com.example.gifeye.data.repository

import com.example.gifeye.data.helpers.ImageStorageHelper
import com.example.gifeye.data.local.BlockedGifEntity
import com.example.gifeye.data.local.GifDao
import com.example.gifeye.data.local.GifEntity
import com.example.gifeye.data.model.GifData
import com.example.gifeye.data.mappers.GifMapper
import com.example.gifeye.data.model.GifImageDetail
import com.example.gifeye.data.model.GifImages
import com.example.gifeye.data.remote.GifApiService
import com.example.gifeye.domain.repository.GifRepositoryInterface
import com.example.gifeye.utils.ApiConstants
import com.example.gifeye.utils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class GifRepository @Inject constructor(
    private val apiService: GifApiService,
    private val gifDao: GifDao,
    private val imageStorageHelper: ImageStorageHelper,
    private val networkUtil: NetworkUtil
) : GifRepositoryInterface {

    override suspend fun searchGifs(query: String, limit: Int, offset: Int): List<GifData> {
        val blockedIds = gifDao.getBlockedGifIds()
        return if (networkUtil.isNetworkAvailable()) {
            val response = apiService.searchGifs(
                ApiConstants.API_KEY,
                query,
                limit,
                offset,
                ApiConstants.DEFAULT_RATING,
                ApiConstants.DEFAULT_LANGUAGE
            )
            val gifs =
                GifMapper.mapApiResponseToGifData(response).filter { !blockedIds.contains(it.id) }

            withContext(Dispatchers.IO) {
                val gifEntities = gifs.map { gif ->
                    val localPath =
                        imageStorageHelper.saveImageLocally(gif.images.original.url, gif.id)
                    GifEntity(
                        gif.id,
                        gif.title,
                        gif.images.original.url,
                        localPath,
                        isDeleted = false
                    )
                }
                gifDao.insertGifs(gifEntities)
            }

            gifs
        } else {
            gifDao.getGifs(limit, offset).map {
                GifData(it.id, it.title, GifImages(GifImageDetail(it.localPath ?: it.url)))
            }
        }
    }

    override suspend fun deleteGif(id: String) {
        withContext(Dispatchers.IO) {
            val gifEntity = gifDao.getGifById(id)
            gifEntity?.localPath?.let { path ->
                val file = File(path)
                if (file.exists()) file.delete()
            }
            gifDao.deleteGifById(id)
            gifDao.insertBlockedGif(BlockedGifEntity(id))
        }
    }
}