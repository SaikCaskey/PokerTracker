package com.github.saikcaskey.pokertracker.domain.components

import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface MainPagerPagePlannerComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun onShowDayDetail(day: LocalDate, hasEvent: Boolean)

    data class UiState(
        val datesWithEvents: List<LocalDate> = emptyList(),
    )
}
