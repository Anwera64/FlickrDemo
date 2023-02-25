package com.example.data

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

    override suspend fun getFlickrPhotos(): NetworkResponse<PhotoCollection> {
        val response: Response<PhotoCollectionResponse> = photoService.fetchImages()
        return NetworkResponse(
            isSuccessFul = response.isSuccessful,
            response = response.body()?.let(::mapPhotoCollectionResponse),
            responseCode = response.code()
        )
    }
}