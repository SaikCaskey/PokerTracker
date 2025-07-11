package com.github.saikcaskey.pokertracker.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPagePlannerComponent
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPagePlannerComponent.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.datetime.LocalDate

class DefaultMainPagerPagePlannerComponent(
    componentContext: ComponentContext,
    private val onCalendarDayClicked: (LocalDate, Boolean) -> Unit,
    eventsRepository: EventRepository,
    dispatchers: CoroutineDispatchers,
) : MainPagerPagePlannerComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<UiState> =
        eventsRepository.getDaysWithEvents(
        ).map { UiState(it) }
            .stateIn(coroutineScope, Eagerly, UiState())

    override fun onShowDayDetail(day: LocalDate, hasEvent: Boolean) =
        onCalendarDayClicked(day, hasEvent)
}
