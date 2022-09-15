package ru.bmstu.ulife.ext

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.UK)

fun String.toTimeFromISO(): Long {
    val tz: TimeZone = TimeZone.getTimeZone("GMT")
    df.timeZone = tz

    try {
        return df.parse(this).time
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return 0
}