package com.example.domain.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val REST_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val USA_FORMAT = "MMMM dd yyyy"

    fun parseTimeString(time: String): Date? {
        val formatter = SimpleDateFormat(REST_FORMAT, Locale.getDefault())
        return formatter.parse(time)
    }

    fun parseToString(time: Date): String? {
        val formatter = SimpleDateFormat(USA_FORMAT, Locale.getDefault())
        return formatter.format(time)
    }
}