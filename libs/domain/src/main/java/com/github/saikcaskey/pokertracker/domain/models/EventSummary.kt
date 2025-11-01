package com.github.saikcaskey.pokertracker.domain.models

data class EventSummary(
    val all: List<Event> = emptyList(),
    val upcoming: List<Event> = emptyList(),
    val today: List<Event> = emptyList(),
)
