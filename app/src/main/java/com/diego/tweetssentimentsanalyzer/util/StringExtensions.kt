package com.diego.tweetssentimentsanalyzer.util

import java.text.SimpleDateFormat
import java.util.*

private val OLD_DATA_FORMAT = "EEE MMM dd hh:mm:ss Z yyyy"
private val NEW_DATA_FORMAT = "MMM dd, yyyy"

fun String.dateFormat(): String {
    val newDate = SimpleDateFormat(OLD_DATA_FORMAT, Locale.ENGLISH).parse(this)
    return SimpleDateFormat(NEW_DATA_FORMAT, Locale.ENGLISH).format(newDate)
}