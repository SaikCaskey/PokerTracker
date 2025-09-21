package com.github.saikcaskey.pokertracker.domain.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlin.time.Instant

fun LocalDate.atStartOfDayInstant(timeZone: TimeZone? = null): Instant {
    return atStartOfDayIn(timeZone ?: TimeZone.Companion.currentSystemDefault())
}

fun LocalDate.atTimeInstant(parsedTime: LocalTime, timeZone: TimeZone? = null): Instant {
    return atTime(parsedTime).toInstant(timeZone ?: TimeZone.currentSystemDefault())
}
