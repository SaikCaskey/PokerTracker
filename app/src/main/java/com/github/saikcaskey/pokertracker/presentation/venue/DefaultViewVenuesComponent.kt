package com.github.saikcaskey.pokertracker.presentation.venue

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.components.ViewVenuesComponent
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.components.ViewVenuesComponent.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class DefaultViewVenuesComponent(
    componentContext: ComponentContext,
    private val venueRepository: VenueRepository,
    private val onShowInsertVenue: () -> Unit,
    private val onShowVenueDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    dispatchers: CoroutineDispatchers,
) : ViewVenuesComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _venueSortOption = MutableStateFlow(VenueSortOption.CREATED_AT_DESC)
    private val _searchOptions = combine(_searchQuery, _venueSortOption, ::VenueSearchFilter)
        .stateIn(coroutineScope, Eagerly, VenueSearchFilter())

    override val uiState: StateFlow<UiState> =
        combine(venueRepository.getAll(), _searchOptions) { venues, searchFilter ->
            val filtered = venues
                .filter { venue ->
                    val query = searchFilter.query.orEmpty()

                    query.isBlank() ||
                            venue.name.contains(query, ignoreCase = true)
                }
                .sortedWith(
                    when (searchFilter.sort) {
                        VenueSortOption.NAME_ASC -> compareBy(Venue::id)
                        VenueSortOption.NAME_DESC -> compareByDescending(Venue::name)
                        VenueSortOption.ID_ASC -> compareBy(Venue::id)
                        VenueSortOption.ID_DESC -> compareByDescending(Venue::id)
                        VenueSortOption.CREATED_AT_ASC -> compareBy(Venue::createdAt)
                        VenueSortOption.CREATED_AT_DESC -> compareByDescending(Venue::createdAt)
                        VenueSortOption.UPDATED_AT_ASC -> compareBy(Venue::updatedAt)
                        VenueSortOption.UPDATED_AT_DESC -> compareByDescending(Venue::updatedAt)
                    }
                )
            UiState(venues, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: VenueSortOption) {
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