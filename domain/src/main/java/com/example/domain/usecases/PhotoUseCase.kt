package com.example.domain.usecases

import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repository: PhotoRepository) {

    suspend fun getLatestPhotos(page: Int = 1): NetworkResponse<PhotoCollection> {
        return repository.getFlickrPhotos()
    }
}