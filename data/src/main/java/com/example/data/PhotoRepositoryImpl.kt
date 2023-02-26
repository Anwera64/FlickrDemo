package com.example.data

import android.util.Log
import com.example.data.entities.PhotoCollectionResponse
import com.example.data.mappers.mapPhotoCollectionResponse
import com.example.data.restservices.FlickrPhotosRestService
import com.example.domain.repositories.PhotoRepository
import com.example.domain.entities.NetworkResponse
import com.example.domain.entities.PhotoCollection
import retrofit2.Response
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoService: FlickrPhotosRestService
) : PhotoRepository {

    override suspend fun getFlickrPhotos(page: Int): NetworkResponse<PhotoCollection> {
        Log.d(this::class.java.name, "requesting page: $page")
        val response: Response<PhotoCollectionResponse> = photoService.fetchImages(page)
        return NetworkResponse(
            isSuccessFul = response.isSuccessful,
            response = response.body()?.let(::mapPhotoCollectionResponse),
            responseCode = response.code()
        )
    }

    override suspend fun searchPhotos(searchTerm: String): NetworkResponse<PhotoCollection> {
        val response: Response<PhotoCollectionResponse> = photoService.searchImages(searchTerm)
        return NetworkResponse(
            isSuccessFul = response.isSuccessful,
            response = response.body()?.let(::mapPhotoCollectionResponse),
            responseCode = response.code()
        )
    }
}