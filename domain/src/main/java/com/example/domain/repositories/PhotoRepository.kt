package com.example.domain.repositories

import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection

interface PhotoRepository {

    suspend fun getFlickrPhotos(): NetworkResponse<PhotoCollection>

    suspend fun searchPhotos(searchTerm: String): NetworkResponse<PhotoCollection>
}