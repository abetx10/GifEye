package com.example.gifeye.di

import com.example.gifeye.data.repository.GifRepository
import com.example.gifeye.domain.repository.GifRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGifRepository(
        gifRepository: GifRepository
    ): GifRepositoryInterface = gifRepository
}