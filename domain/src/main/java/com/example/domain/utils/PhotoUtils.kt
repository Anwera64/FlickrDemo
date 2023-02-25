package com.example.domain.utils


fun formatPhotoUrl(id: String, serverId: String, secret: String, sizeSuffix: String = "c"): String {
    return "https://live.staticflickr.com/$serverId/${id}_${secret}_$sizeSuffix.jpg"
}