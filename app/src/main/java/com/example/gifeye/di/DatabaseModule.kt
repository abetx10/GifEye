package com.example.gifeye.di

import android.content.Context
import androidx.room.Room
import com.example.gifeye.data.local.GifDao
import com.example.gifeye.data.local.GifDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GifDatabase {
        return Room.databaseBuilder(
            context,
            GifDatabase::class.java,
            "gif_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGifDao(database: GifDatabase): GifDao {
        return database.gifDao()
    }
}