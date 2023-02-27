package com.example.data

import com.example.data.entities.PhotoCollectionResponse
import com.example.data.mappers.mapPhotoCollectionResponse
import com.example.data.restservices.FlickrPhotosRestService
import com.example.domain.entities.DataResponse
import com.example.domain.entities.PhotoCollection
import com.example.domain.repositories.PhotoRepository
import retrofit2.Response
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoService: FlickrPhotosRestService
) : PhotoRepository {

    override suspend fun getFlickrPhotos(page: Int): DataResponse<PhotoCollection> {
        val response: Response<PhotoCollectionResponse> = photoService.fetchImages(page)
        return DataResponse(
            isSuccessFul = response.isSuccessful,
            response = response.body()?.let(::mapPhotoCollectionResponse),
        )
    }

    override suspend fun searchPhotos(
        searchTerm: String,
        page: Int
    ): DataResponse<PhotoCollection> {
        val response: Response<PhotoCollectionResponse> = photoService.searchImages(
            searchTerm = searchTerm,
            page = page
        )
        return DataResponse(
            isSuccessFul = response.isSuccessful,
            response = response.body()?.let(::mapPhotoCollectionResponse),
        )
    }
}