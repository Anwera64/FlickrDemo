package com.example.domain.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun parseTimeString(time: String): Date? {
        val formatter = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
        return formatter.parse(time)
    }
}