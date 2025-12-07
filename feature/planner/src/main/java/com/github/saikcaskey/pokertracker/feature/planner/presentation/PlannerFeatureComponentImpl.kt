package com.github.saikcaskey.pokertracker.feature.planner.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class PlannerFeatureComponentImpl(
    componentContext: ComponentContext,
    private val onCalendarDayClicked: (LocalDate, Boolean) -> Unit,
    private val onFinished: () -> Unit,
    eventsRepository: EventRepository,
    dispatchers: CoroutineDispatchers,
) : PlannerFeatureComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<PlannerFeatureComponent.UiState> = eventsRepository.getDaysWithEvents()
        .map(PlannerFeatureComponent::UiState)
        .stateIn(coroutineScope, Eagerly, PlannerFeatureComponent.UiState())

    override fun onShowDayDetail(day: LocalDate, hasEvent: Boolean) =
        onCalendarDayClicked(day, hasEvent)

    override fun onBackClicked() = onFinished()
}
