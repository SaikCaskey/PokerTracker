package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.*

fun String?.asInstantOrNull(): Instant? {
    return this?.let { runCatching(Instant::parse).getOrNull() }
}
