package com.example.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoCollectionResponse(
    @JsonProperty("photos")
    val photos: PhotosResponse
)