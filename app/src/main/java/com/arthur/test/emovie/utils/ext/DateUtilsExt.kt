package com.arthur.test.emovie.utils.ext

import java.text.SimpleDateFormat
import java.util.*

fun String.getDate(format: String = "yyyy-MM-dd"): Date {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.parse(this)
}

fun String.getCalendar(format: String = "yyyy-MM-dd"): Calendar {
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = getDate(format)
    return cal
}

fun String.getYearFromDate() : Int = getCalendar().get(Calendar.YEAR)
