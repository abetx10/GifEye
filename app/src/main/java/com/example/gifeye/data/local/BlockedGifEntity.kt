package com.example.gifeye.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_gif_table")
data class BlockedGifEntity(
    @PrimaryKey val id: String
)