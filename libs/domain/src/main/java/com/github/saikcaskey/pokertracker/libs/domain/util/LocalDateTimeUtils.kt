package com.github.saikcaskey.pokertracker.libs.domain.util

import com.github.saikcaskey.pokertracker.libs.domain.extensions.asLocalDateTime
import kotlinx.datetime.LocalDateTime

fun nowAsLocalDateTime(): LocalDateTime {
    return nowAsInstant().asLocalDateTime()
}
