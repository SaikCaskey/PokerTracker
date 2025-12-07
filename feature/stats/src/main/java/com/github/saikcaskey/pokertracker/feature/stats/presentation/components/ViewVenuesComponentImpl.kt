package com.github.saikcaskey.pokertracker.feature.stats.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewVenuesComponent
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import com.github.saikcaskey.pokertracker.libs.domain.repository.VenueRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class ViewVenuesComponentImpl(
    componentContext: ComponentContext,
    private val venueRepository: VenueRepository,
    private val onShowInsertVenue: () -> Unit,
    private val onShowVenueDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    dispatchers: CoroutineDispatchers,
) : ViewVenuesComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _venueSortOption = MutableStateFlow(ViewVenuesComponent.VenueSortOption.CREATED_AT_DESC)
    private val _searchOptions = combine(_searchQuery, _venueSortOption,
        ViewVenuesComponent::VenueSearchFilter
    )
        .stateIn(coroutineScope, Eagerly, ViewVenuesComponent.VenueSearchFilter())

    override val uiState: StateFlow<ViewVenuesComponent.UiState> =
        combine(venueRepository.getAll(), _searchOptions) { venues, searchFilter ->
            val filtered = venues
                .filter { venue ->
                    val query = searchFilter.query.orEmpty()

                    query.isBlank() ||
                            venue.name.contains(query, ignoreCase = true)
                }
                .sortedWith(
                    when (searchFilter.sort) {
                        ViewVenuesComponent.VenueSortOption.NAME_ASC -> compareBy(Venue::id)
                        ViewVenuesComponent.VenueSortOption.NAME_DESC -> compareByDescending(Venue::name)
                        ViewVenuesComponent.VenueSortOption.ID_ASC -> compareBy(Venue::id)
                        ViewVenuesComponent.VenueSortOption.ID_DESC -> compareByDescending(Venue::id)
                        ViewVenuesComponent.VenueSortOption.CREATED_AT_ASC -> compareBy(Venue::createdAt)
                        ViewVenuesComponent.VenueSortOption.CREATED_AT_DESC -> compareByDescending(Venue::createdAt)
                        ViewVenuesComponent.VenueSortOption.UPDATED_AT_ASC -> compareBy(Venue::updatedAt)
                        ViewVenuesComponent.VenueSortOption.UPDATED_AT_DESC -> compareByDescending(Venue::updatedAt)
                    }
                )
            ViewVenuesComponent.UiState(venues, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, ViewVenuesComponent.UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: ViewVenuesComponent.VenueSortOption) {
        _venueSortOption.value = sortOption
    }

    override fun onShowVenueDetailClicked(venueId: Long) = onShowVenueDetail(venueId)

    override fun onShowInsertVenueClicked() = onShowInsertVenue()

    override fun onDeleteVenueClicked(id: Long) {
        coroutineScope.launch {
            runCatching { venueRepository.deleteById(id) }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onDeleteAllVenuesClicked() {
        coroutineScope.launch {
            runCatching { venueRepository.deleteAll() }
                .onFailure(Throwable::printStackTrace)
        }
    }
}
