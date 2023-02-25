package com.example.domain.entities

data class PhotoCollection(
    val page: Int,
    val pages: Int,
    val photos: List<FlickrPhoto>
)