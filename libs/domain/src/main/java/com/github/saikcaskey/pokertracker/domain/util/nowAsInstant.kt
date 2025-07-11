package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.*

fun nowAsInstant(): Instant {
    return Clock.System.now()
}