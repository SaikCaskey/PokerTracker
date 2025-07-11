package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.Instant

fun LocalDate.atTimeInstant(parsedTime: LocalTime, timeZone: TimeZone? = null): Instant {
    return atTime(parsedTime).toInstant(timeZone ?: TimeZone.currentSystemDefault())
}
