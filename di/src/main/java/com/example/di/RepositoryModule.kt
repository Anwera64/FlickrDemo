package com.example.di

import com.example.data.PhotoRepositoryImpl
import com.example.data.restservices.FlickrPhotosRestService
import com.example.domain.repositories.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesPhotoRepository(photosRestService: FlickrPhotosRestService): PhotoRepository {
        return PhotoRepositoryImpl(photosRestService)
    }
}