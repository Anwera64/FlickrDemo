package com.example.domain.entities

/**
 * A data class that represents a PhotoCollection.
 *
 * @param page -> current page that has been queried
 * @param pages -> max pages that can be queried
 * @param photos -> photos in the collection
 * @param searchTerm -> indicates the search query for the collection if present
 */
data class PhotoCollection(
    val page: Int,
    val pages: Int,
    val photos: List<FlickrPhoto>,
    var searchTerm: String? = null
) {

    /**
     * Indicates whether the collection comes from a search query.
     */
    val isSearch: Boolean
        get() = !searchTerm?.trim().isNullOrEmpty()
}