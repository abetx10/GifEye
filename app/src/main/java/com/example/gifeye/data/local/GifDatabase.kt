package com.example.gifeye.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifEntity::class, BlockedGifEntity::class], version = 3, exportSchema = true)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}