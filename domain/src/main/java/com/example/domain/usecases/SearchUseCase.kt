package com.example.domain.usecases

import com.example.domain.entities.DataResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    PaginatedUseCase<PhotoCollection>() {

    /**
     * Indicates the current search term. If it's empty then no search is being currently run.
     */
    private var currentSearchTerm = ""

    suspend fun searchPhotos(searchTerm: String): DataResponse<PhotoCollection> {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) {
            return DataResponse(false, null, "Empty search input.")
        }
        val searchResult: DataResponse<PhotoCollection> = photoRepository.searchPhotos(
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

    override val paginatedQuery: suspend (page: Int) -> DataResponse<PhotoCollection>
        get() = { page -> photoRepository.searchPhotos(currentSearchTerm, page) }

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