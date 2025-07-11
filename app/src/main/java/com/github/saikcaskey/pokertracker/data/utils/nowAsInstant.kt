package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.*

fun nowAsInstant(): Instant {
    return Clock.System.now()
}