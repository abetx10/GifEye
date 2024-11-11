package com.example.gifeye.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_table")
data class GifEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val localPath: String?,
    val isDeleted: Boolean = false
)