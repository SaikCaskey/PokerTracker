package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun String?.asInstantOrNow(): Instant {
    return this?.let {
        runCatching(Instant::parse).getOrNull()
    } ?: Clock.System.now()
}
