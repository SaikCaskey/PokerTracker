package com.github.saikcaskey.pokertracker.libs.domain.extensions

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.Instant

fun LocalDateTime.toInstant(): Instant {
    return toInstant(TimeZone.currentSystemDefault())
}

fun LocalDateTime.plusMinutes(minutes: Int): Instant {
    return toInstant().plus(minutes, DateTimeUnit.MINUTE)
}
