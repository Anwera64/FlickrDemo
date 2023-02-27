package com.example.domain.usecases

import com.example.domain.entities.DataResponse

abstract class PaginatedUseCase<T> {

    /**
     * Indicates which is the current page that is being shown to the user
     */
    protected var currentPage = 0

    /**
     * Indicates the max pages that can be queried from the cloud.
     */
    protected var maxPages = 0

    /**
     * Request the next page if possible. If there are no more pages available return an error.
     */
    suspend fun requestNexPage(): DataResponse<T> {
        if (currentPage == maxPages) {
            return DataResponse(false, null, "No more pages")
        }

        return paginatedQuery(currentPage++)
    }

    abstract val paginatedQuery: suspend (page: Int) -> DataResponse<T>
}