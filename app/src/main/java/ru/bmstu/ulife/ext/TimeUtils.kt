package ru.bmstu.ulife.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import kotlin.math.max
import kotlin.math.min

object TimeUtils {
    fun toDuration(start: String?, end: String?): String {
        if (start.isNullOrBlank() || end.isNullOrBlank()) {
            return ""
        }

        val currentTime = System.currentTimeMillis() / 1000
        var startTime = start.toTimeFromISO() / 1000
        val endTime = end.toTimeFromISO() / 1000
        if (currentTime in (startTime + 1) until endTime) {
            startTime = currentTime
        }
        val diffTime = max(startTime, endTime) - min(startTime, endTime)
        val hours = diffTime / (60 * 60)
        val minutes = diffTime / 60
        val days = diffTime / (60 * 60 * 24)

        return when {
            days > 0 -> {
                return String.format("%d дн. ", days.toInt())
            }
            hours > 0 -> {
                return String.format("%d ч. ", hours.toInt())
            }
            else -> String.format("%d мин. ", minutes.toInt())
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromTimestamp(s: String): String { //    2020-07-09T20:00:00Z
        val year = s.substring(0, 4)
        val month = s.substring(5, 7)
        val day = s.substring(8, 10)
        return "$day.$month.$year"
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTimeFromTimestamp(s: String): String { //    2020-07-09T20:00:00Z
        val year = s.substring(0, 4)
        val month = s.substring(5, 7)
        val day = s.substring(8, 10)
        val hours = s.substring(11, 13)
        val minutes = s.substring(14, 16)
        val seconds = s.substring(17, 19)
        return "$day.$month.$year, $hours:$minutes"
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTimeFromLongTimestamp(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("d.MM.yyyy HH:mm")
        return format.format(date)
    }
}
