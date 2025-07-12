package com.github.saikcaskey.pokertracker.domain.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun String?.asInstantOrNow(): Instant {
    return this?.let {
        runCatching(Instant.Companion::parse).getOrNull()
    } ?: Clock.System.now()
}

fun String?.asInstantOrNull(): Instant? {
    return this?.let { runCatching(Instant::parse).getOrNull() }
}