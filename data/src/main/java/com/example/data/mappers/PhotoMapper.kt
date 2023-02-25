package com.example.data.mappers

import com.example.data.entities.PhotoCollectionResponse
import com.example.domain.entities.FlickrPhoto
import com.example.domain.entities.PhotoCollection
import com.example.domain.utils.DateUtil
import com.example.domain.utils.formatPhotoUrl
import java.util.*

fun mapPhotoCollectionResponse(
    response: PhotoCollectionResponse
): PhotoCollection = with(response) {
    return PhotoCollection(
        page = photos.page,
        pages = photos.pages,
        photos = photos.photoList.map { photoResponse ->
            FlickrPhoto(
                id = photoResponse.id,
                description = photoResponse.title,
                username = photoResponse.ownerName,
                date = DateUtil.parseTimeString(photoResponse.dateTaken) ?: Date(),
                photoUrl = formatPhotoUrl(
                    id = photoResponse.id,
                    serverId = photoResponse.server,
                    secret = photoResponse.secret
                )
            )
        }
    )
}