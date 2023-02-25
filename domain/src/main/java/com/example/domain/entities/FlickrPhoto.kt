package com.example.domain.entities

import java.util.*

data class FlickrPhoto(
    val id: String,
    val description: String?,
    val username: String,
    val date: Date,
    val photoUrl: String
)