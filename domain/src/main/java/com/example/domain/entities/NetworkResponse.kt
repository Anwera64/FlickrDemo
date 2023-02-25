package com.example.domain.entities

data class NetworkResponse<T>(
    val isSuccessFul: Boolean,
    val response: T?,
    val responseCode: Int,
    val error: String? = null
)