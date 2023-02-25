package com.example.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoResponse(
    @JsonProperty("id")
    var id: String,
    @JsonProperty("title")
    var title: String,
    @JsonProperty("ownername")
    var ownerName: String,
    @JsonProperty("secret")
    var secret: String,
    @JsonProperty("server")
    var server: String,
    @JsonProperty("datetaken")
    val dateTaken: String
)
