package com.example.domain.entities

data class DataResponse<T>(
    val isSuccessFul: Boolean,
    val response: T?,
    val error: String? = null
)