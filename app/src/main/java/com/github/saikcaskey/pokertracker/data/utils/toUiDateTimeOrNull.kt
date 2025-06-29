package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant

fun Instant?.toUiDateTimeOrNull(): String? {
    val localDateTime = this?.toLocalDateTime(TimeZone.currentSystemDefault()) ?: return null
    val formatter = LocalDateTime.Format {
        year();char('-');monthNumber();char('-');dayOfMonth();char(' ')
        hour();char(':');minute()
    }
    return formatter.format(localDateTime)
}