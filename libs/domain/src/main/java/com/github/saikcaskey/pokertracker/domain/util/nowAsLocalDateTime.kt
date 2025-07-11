package com.github.saikcaskey.pokertracker.domain.util

import kotlinx.datetime.*

fun nowAsLocalDateTime(): LocalDateTime {
    return nowAsInstant().asLocalDateTime()
}
