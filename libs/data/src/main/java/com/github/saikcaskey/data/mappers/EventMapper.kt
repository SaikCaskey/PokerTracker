package com.github.saikcaskey.data.mappers

import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNull
import com.github.saikcaskey.pokertracker.database.Event
import com.github.saikcaskey.pokertracker.domain.models.GameType
import com.github.saikcaskey.pokertracker.domain.models.Event as DomainEvent

fun Event.toDomain(): DomainEvent {
    return DomainEvent(
        id = id,
        venueId = venue_id,
        name = name,
        date = date.asInstantOrNow(),
        gameType = GameType.valueOf(game_type),
        description = description,
        createdAt = created_at.asInstantOrNow(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}
