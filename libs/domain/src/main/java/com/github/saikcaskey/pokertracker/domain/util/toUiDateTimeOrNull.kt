package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

fun Instant?.toUiDateTimeOrNull(): String? {
    val localDateTime = this?.asLocalDateTime() ?: return null
    val formatter = LocalDateTime.Format {
        year();char('-');monthNumber();char('-');dayOfMonth();char(' ')
        hour();char(':');minute()
    }
    return formatter.format(localDateTime)
}