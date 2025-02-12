package com.dxid.breadnews.util


import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.Locale
import java.util.TimeZone

fun formatDate(dateString: String?, inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'", outputFormat: String = "EEE, dd MMMM HH.mm"): String? {
    return convertToYearMonthFormat(dateString, inputFormat, outputFormat)
}

fun convertToYearMonthFormat(dateString: String?, inputFormat: String, outputFormat: String, timeZone: TimeZone = TimeZone.getTimeZone("Asia/Jakarta")): String? {
    if (dateString.isNullOrEmpty()) {
        return null
    }
    return try {
        val sdfInput = SimpleDateFormat(inputFormat, Locale("id", "ID"))
        sdfInput.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdfInput.parse(dateString)

        val sdfOutput = SimpleDateFormat(outputFormat, Locale("id", "ID"))
        sdfOutput.timeZone = timeZone
        sdfOutput.format(date!!)
    } catch (e: ParseException) {
        null
    }
}