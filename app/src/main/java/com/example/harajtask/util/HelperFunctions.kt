package com.example.harajtask.util

import android.content.Context
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun Context.getTranDateTime() = SimpleDateFormat("ddMMyyhhmmss", Locale.US).format(Date())

fun setDateFormat(dateOfBirth: Long): String {
    val date :DateFormat= SimpleDateFormat("MMMM dd , yyyy")
    return date.format(Date(dateOfBirth))
    //SimpleDateFormat("ddMMyyyy", Locale.US).format(date).toString()
}

fun setDateTime(tranDateTime: String): String? {
    val dateFormat = SimpleDateFormat("MMMM dd , yyyy", Locale.US)
    var date: Date? = null
    try {
        date = dateFormat.parse(tranDateTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val dateFormatNew = SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.US)
    return dateFormatNew.format(date)
}
