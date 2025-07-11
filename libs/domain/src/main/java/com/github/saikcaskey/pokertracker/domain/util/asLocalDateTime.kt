package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant

fun Instant.asLocalDateTime(timeZone: TimeZone? = null): LocalDateTime {
    return toLocalDateTime(timeZone ?: TimeZone.currentSystemDefault())
}