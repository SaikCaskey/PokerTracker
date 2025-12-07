package com.github.saikcaskey.pokertracker.libs.domain.models

import kotlin.time.Instant

data class Event(
    val id: Long,
    val venueId: Long?,
    val name: String?,
    val date: Instant? = null,
    val gameType: GameType,
    val description: String?,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
