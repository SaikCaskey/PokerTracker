package com.github.saikcaskey.pokertracker.data.utils

import kotlinx.datetime.*

fun nowAsLocalDateTime(): LocalDateTime {
    return nowAsInstant().asLocalDateTime()
}
