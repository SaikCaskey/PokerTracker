package com.github.saikcaskey.pokertracker.planner

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class PlannerDayDetailComponentImpl(
    private val componentContext: ComponentContext,
    private val date: LocalDate,
    eventRepository: EventRepository,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowInsertEvent: () -> Unit,
    private val onFinished: () -> Unit,
    dispatchers: CoroutineDispatchers,
) : PlannerDayDetailComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<PlannerDayDetailComponent.UiState> =
        eventRepository.getByDate(date)
            .map { events -> PlannerDayDetailComponent.UiState(date = date, events = events) }
            .stateIn(scope, SharingStarted.Eagerly, PlannerDayDetailComponent.UiState(date = date))

    override fun onShowEventDetailClicked(eventId: Long) {
        onShowEventDetail(eventId)
    }

    override fun onShowInsertEventClicked() {
        onShowInsertEvent()
    }

    override fun onBackClicked() {
        onFinished()
    }
}