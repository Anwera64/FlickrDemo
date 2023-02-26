package com.example.domain.usecases

import android.util.Log
import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repository: PhotoRepository) {

    private var currentPage = 0
    private var maxPages = 0

    suspend fun getLatestPhotos(): NetworkResponse<PhotoCollection> {
        val response = repository.getFlickrPhotos(0)
        if (response.isSuccessFul) {
            currentPage = response.response?.page ?: 0
            maxPages = response.response?.pages ?: 0
        }
        return response
    }

    suspend fun requestNexPage(): NetworkResponse<PhotoCollection> {
        if (currentPage == maxPages) {
            return NetworkResponse(false, null, 400, "No more pages")
        }

        return repository.getFlickrPhotos(currentPage++)
    }
}