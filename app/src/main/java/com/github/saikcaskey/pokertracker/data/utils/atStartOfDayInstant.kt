package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

fun LocalDate.atStartOfDayInstant(timeZone: TimeZone? = null): Instant {
    return atStartOfDayIn(timeZone ?: TimeZone.currentSystemDefault())
}
