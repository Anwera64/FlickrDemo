package com.example.domain.usecases

import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    suspend fun searchPhotos(searchTerm: String): NetworkResponse<PhotoCollection> {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) {
            return NetworkResponse(false, null, 400, "Empty search input.")
        }
        return photoRepository.searchPhotos(searchTerm)
    }
}