package com.example.domain.repositories

import com.example.domain.entities.DataResponse
import com.example.domain.entities.PhotoCollection

interface PhotoRepository {

    suspend fun getFlickrPhotos(page: Int): DataResponse<PhotoCollection>

    suspend fun searchPhotos(searchTerm: String, page: Int): DataResponse<PhotoCollection>
}