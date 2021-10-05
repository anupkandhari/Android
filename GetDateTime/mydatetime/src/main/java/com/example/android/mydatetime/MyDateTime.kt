package com.example.android.mydatetime

import java.text.SimpleDateFormat
import java.util.*


object MyDateTime {


    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDate() : String {
        return Calendar.getInstance().time.toString("dd/MM/yyyy")
    }

    fun getCurrentTime(): String {
        return Calendar.getInstance().time.toString("HH:mm:ss")
    }

}