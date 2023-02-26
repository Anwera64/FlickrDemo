package com.example.domain

import com.example.domain.repositories.PhotoRepository
import com.example.domain.usecases.PhotoUseCase
import com.example.domain.usecases.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DomainModule {

    @Provides
    fun providesPhotoUseCase(repository: PhotoRepository): PhotoUseCase {
        return PhotoUseCase(repository)
    }

    @Provides
    fun providesSearchUseCase(repository: PhotoRepository): SearchUseCase {
        return SearchUseCase(repository)
    }
}
