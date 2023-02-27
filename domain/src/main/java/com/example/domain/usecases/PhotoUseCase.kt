package com.example.domain.usecases

import com.example.domain.entities.DataResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repository: PhotoRepository) :
    PaginatedUseCase<PhotoCollection>() {

    suspend fun getLatestPhotos(): DataResponse<PhotoCollection> {
        val response = repository.getFlickrPhotos(0)
        if (response.isSuccessFul) {
            currentPage = response.response?.page ?: 0
            maxPages = response.response?.pages ?: 0
        }
        return response
    }

    override val paginatedQuery: suspend (page: Int) -> DataResponse<PhotoCollection>
        get() = { page -> repository.getFlickrPhotos(page) }
}