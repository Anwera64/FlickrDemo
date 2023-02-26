package com.example.domain.usecases

import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val photoRepository: PhotoRepository) {

    private var currentPage = 0
    private var maxPages = 0
    private var currentSearchTerm = ""

    suspend fun searchPhotos(searchTerm: String): NetworkResponse<PhotoCollection> {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) {
            return NetworkResponse(false, null, 400, "Empty search input.")
        }
        val searchResult: NetworkResponse<PhotoCollection> = photoRepository.searchPhotos(
            searchTerm = searchTerm,
            page = 0
        )
        if (searchResult.isSuccessFul) {
            currentPage = searchResult.response?.page ?: 0
            maxPages = searchResult.response?.pages ?: 0
            currentSearchTerm = searchTerm
            searchResult.response?.searchTerm = searchTerm
        }
        return searchResult
    }

    suspend fun requestNexPage(): NetworkResponse<PhotoCollection> {
        if (currentPage == maxPages) {
            return NetworkResponse(false, null, 400, "No more pages")
        }

        return photoRepository.searchPhotos(currentSearchTerm, currentPage++)
    }

    /**
     * Clears the search parameters and resets the page counters. If there was no search being run
     * return false, otherwise return true to confirm that the state was reset.
     */
    fun clearSearch(): Boolean {
        currentPage = 0
        maxPages = 0

        return if (currentSearchTerm.isBlank() || currentSearchTerm.isEmpty()) {
            false
        } else {
            currentSearchTerm = ""
            true
        }
    }
}