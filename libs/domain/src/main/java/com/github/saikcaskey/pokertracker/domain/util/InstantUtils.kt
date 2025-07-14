package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.*

fun nowAsInstant(): Instant = Clock.System.now()
