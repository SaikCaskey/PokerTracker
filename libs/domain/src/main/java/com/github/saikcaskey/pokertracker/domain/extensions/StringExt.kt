package com.github.saikcaskey.pokertracker.domain.extensions

import kotlin.time.Clock
import kotlin.time.Instant

fun String?.asInstantOrNow(): Instant {
    return this?.let {
        runCatching(Instant.Companion::parse).getOrNull()
    } ?: Clock.System.now()
}

fun String?.asInstantOrNull(): Instant? {
    return this?.let { runCatching(Instant::parse).getOrNull() }
}
