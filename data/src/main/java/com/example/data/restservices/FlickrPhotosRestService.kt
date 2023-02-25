package com.example.data.restservices

import com.example.data.BuildConfig
import com.example.data.entities.PhotoCollectionResponse
import retrofit2.Response
import retrofit2.http.GET

interface FlickrPhotosRestService {

    @GET("?method=flickr.photos.getRecent&format=json&api_key=${BuildConfig.FLICK_API_KEY}&nojsoncallback=1&extras=owner_name,date_taken")
    suspend fun fetchImages(): Response<PhotoCollectionResponse>
}