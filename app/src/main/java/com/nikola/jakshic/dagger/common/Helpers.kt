package com.nikola.jakshic.dagger.common

import android.content.Context
import com.nikola.jakshic.dagger.R
import java.util.concurrent.TimeUnit

/**
 * @param endTime in seconds
 */
fun timeElapsed(context: Context, endTime: Long): String {
    val endTimeInMillis = TimeUnit.SECONDS.toMillis(endTime)
    val timeElapsed = System.currentTimeMillis() - endTimeInMillis

    val years = TimeUnit.MILLISECONDS.toDays(timeElapsed) / 365
    val months = TimeUnit.MILLISECONDS.toDays(timeElapsed) / 30
    val days = TimeUnit.MILLISECONDS.toDays(timeElapsed)
    val hours = TimeUnit.MILLISECONDS.toHours(timeElapsed)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed)

    return when {
        years > 0 -> context.resources.getQuantityString(R.plurals.year, years.toInt(), years)
        months > 0 -> context.resources.getQuantityString(R.plurals.month, months.toInt(), months)
        days > 0 -> context.resources.getQuantityString(R.plurals.day, days.toInt(), days)
        hours > 0 -> context.resources.getQuantityString(R.plurals.hour, hours.toInt(), hours)
        else -> context.resources.getQuantityString(R.plurals.minute, minutes.toInt(), minutes)
    }
}