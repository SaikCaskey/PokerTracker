package com.github.saikcaskey.pokertracker.domain.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Instant.asLocalDateTime(timeZone: TimeZone? = null): LocalDateTime {
    return toLocalDateTime(timeZone ?: TimeZone.Companion.currentSystemDefault())
}

fun Instant?.toUiDateTimeOrNull(): String? {
    val localDateTime = this?.asLocalDateTime() ?: return null
    val formatter = LocalDateTime.Format {
        year();char('-');
        monthNumber();char('-');
        day(padding = Padding.ZERO)
        char(' ');hour();char(':');minute()
    }
    return formatter.format(localDateTime)
}
