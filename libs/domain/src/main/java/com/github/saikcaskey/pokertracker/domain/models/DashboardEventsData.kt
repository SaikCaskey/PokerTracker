package com.github.saikcaskey.pokertracker.domain.models

data class DashboardEventsData(
    val recentEvents: List<Event> = emptyList(),
    val todayEvents: List<Event> = emptyList(),
    val upcomingEvents: List<Event> = emptyList(),
)
