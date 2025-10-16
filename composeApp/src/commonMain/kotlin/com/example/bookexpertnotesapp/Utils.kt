package com.example.bookexpertnotesapp

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
fun currentDateDDMMYYYY() : String{
    val instant = Clock.System.now()
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(timeZone)
    val formatter = LocalDateTime.Format { byUnicodePattern("dd-MM-yyyy") }
    val formattedDate = localDateTime.format(formatter)
    return formattedDate
}