package com.github.saikcaskey.pokertracker.domain.util

import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import kotlinx.datetime.LocalDateTime

fun nowAsLocalDateTime(): LocalDateTime {
    return nowAsInstant().asLocalDateTime()
}
