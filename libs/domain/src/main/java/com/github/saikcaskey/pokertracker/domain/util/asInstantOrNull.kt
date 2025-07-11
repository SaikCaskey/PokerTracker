package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.*

fun String?.asInstantOrNull(): Instant? {
    return this?.let { runCatching(Instant::parse).getOrNull() }
}
