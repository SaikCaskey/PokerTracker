package com.github.saikcaskey.pokertracker.feature.planner.presentation

import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface PlannerFeatureComponent : FeatureComponent {
    val uiState: StateFlow<UiState>

    fun onShowDayDetail(day: LocalDate, hasEvent: Boolean)
    fun onBackClicked()

    data class UiState(
        val datesWithEvents: List<LocalDate> = emptyList(),
    )
}
