package com.saikcaskey.github.pokertracker.shared.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.datetime.LocalDate

class MainPagerPagePlannerComponent(
    componentContext: ComponentContext,
    private val onCalendarDayClicked: (LocalDate, Boolean) -> Unit,
    eventsRepository: EventRepository,
    dispatchers: CoroutineDispatchers,
) : MainPagerPageComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    val uiState: StateFlow<UiState> =
        eventsRepository.getDaysWithEvents(
        ).map { UiState(it) }
            .stateIn(coroutineScope, Eagerly, UiState())

    fun onShowDayDetail(day: LocalDate, hasEvent: Boolean) = onCalendarDayClicked(day, hasEvent)

    data class UiState(
        val datesWithEvents: List<LocalDate> = emptyList(),
    )
}
