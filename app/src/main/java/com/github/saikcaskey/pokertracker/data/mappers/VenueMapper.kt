package com.github.saikcaskey.pokertracker.data.mappers

import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNull
import com.github.saikcaskey.pokertracker.database.Venue
import com.github.saikcaskey.pokertracker.domain.models.Venue as DomainVenue

fun Venue.toDomain() = DomainVenue(
    id = id,
    name = name,
    address = address,
    description = description,
    createdAt = created_at.asInstantOrNow(),
    updatedAt = updated_at.asInstantOrNull()
)
