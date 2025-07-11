package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun String?.asInstantOrNow(): Instant {
    return this?.let {
        runCatching(Instant::parse).getOrNull()
    } ?: Clock.System.now()
}
