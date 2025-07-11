package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface ViewVenuesComponent {
    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onSearchQueryChanged(query: String?)
    fun onShowVenueDetailClicked(venueId: Long)
    fun onDeleteAllVenuesClicked()
    fun onFilterOptionChanged(sortOption: VenueSortOption)
    fun onShowInsertVenueClicked()
    fun onDeleteVenueClicked(id: Long)

    data class UiState(
        val events: List<Venue> = emptyList(),
        val filtered: List<Venue> = emptyList(),
        val searchFilter: VenueSearchFilter = VenueSearchFilter(),
    )

    enum class VenueSortOption {
        NAME_ASC, NAME_DESC,
        ID_ASC, ID_DESC,
        CREATED_AT_ASC, CREATED_AT_DESC,
        UPDATED_AT_ASC, UPDATED_AT_DESC,
    }

    data class VenueSearchFilter(
        val query: String? = null,
        val sort: VenueSortOption = VenueSortOption.CREATED_AT_DESC,
    )
}
