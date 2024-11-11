package com.example.gifeye.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifs(gifs: List<GifEntity>)

    @Query("SELECT * FROM gif_table WHERE isDeleted = 0 LIMIT :limit OFFSET :offset")
    suspend fun getGifs(limit: Int, offset: Int): List<GifEntity>

    @Query("SELECT * FROM gif_table WHERE id = :id")
    suspend fun getGifById(id: String): GifEntity?

    @Query("DELETE FROM gif_table WHERE id = :id")
    suspend fun deleteGifById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlockedGif(blockedGifEntity: BlockedGifEntity)

    @Query("SELECT id FROM blocked_gif_table")
    suspend fun getBlockedGifIds(): List<String>
}