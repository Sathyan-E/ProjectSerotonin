package com.example.projectserotonin.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class CommonUtils() {
    /**
        This function reads json from assets folder and returns it as string
     **/
    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    /**
        This function converts timestamp to local time
     **/
    fun getLocalTimeFromTimeStamp(timeStamp: String) : String {
        val pattern = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val localDateTime = LocalDateTime.parse(timeStamp, pattern)
        val lo =  localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        return lo
    }

    /**
        This function converts to 12 hour format from 24 hour format
     **/
    fun convertTo12HourFormat(timeString: String): String {
        if (timeString.isEmpty()) {
            throw IllegalArgumentException("Invalid time format. Please use hh:mm:ss format.")
        }

        val (hoursString, minutesString) = timeString.split(":")
        val hours = hoursString.toInt()
        val minutes = minutesString.toInt()

        val period = when (hours) {
            in 0..11 -> "AM"
            in 12..23 -> "PM"
            else -> throw IllegalArgumentException("Invalid hour value")
        }

        val formattedHours = when (hours) {
            0 -> 12
            else -> hours % 12
        }

        return "%02d:%02d%s".format(formattedHours, minutes, period)
    }

    /**
        This function converts date to string
     **/
    fun getFormattedDateStringFromDate(data: Long) : String {
        val d = Date(data)
        val formatter =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(d)
    }

    fun getFormattedTimeStringFromDate(date: Long) : String {
        val d = Date(date)
        val formatter =  SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formatter.format(d)
    }
}