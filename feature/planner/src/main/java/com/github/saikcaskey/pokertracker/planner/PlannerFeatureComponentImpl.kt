package com.github.saikcaskey.pokertracker.planner

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.PlannerFeatureComponent
import com.github.saikcaskey.pokertracker.domain.components.PlannerFeatureComponent.UiState
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class PlannerFeatureComponentImpl(
    componentContext: ComponentContext,
    private val onCalendarDayClicked: (LocalDate, Boolean) -> Unit,
    eventsRepository: EventRepository,
    dispatchers: CoroutineDispatchers,
) : PlannerFeatureComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<UiState> = eventsRepository.getDaysWithEvents()
        .map(::UiState)
        .stateIn(coroutineScope, Eagerly, UiState())

    override fun onShowDayDetail(day: LocalDate, hasEvent: Boolean) =
        onCalendarDayClicked(day, hasEvent)
}