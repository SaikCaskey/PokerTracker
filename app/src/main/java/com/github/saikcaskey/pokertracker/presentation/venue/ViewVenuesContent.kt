package com.github.saikcaskey.pokertracker.presentation.venue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.domain.components.ViewVenuesComponent
import com.github.saikcaskey.pokertracker.presentation.common.appbar.TopAppBarItemViewer
import com.github.saikcaskey.pokertracker.presentation.common.inputform.InputDropdownSimple
import com.github.saikcaskey.pokertracker.domain.components.ViewVenuesComponent.VenueSortOption

@Composable
fun ViewVenuesContent(component: ViewVenuesComponent) {
    val uiState by component.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarItemViewer(
                "All Venues",
                onBackClicked = component::onBackClicked,
                onShowInsertItemClicked = component::onShowInsertVenueClicked,
                onDeleteAllItemsClicked = component::onDeleteAllVenuesClicked,
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchFilter.query.orEmpty(),
                onValueChange = component::onSearchQueryChanged,
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            InputDropdownSimple(
                selected = uiState.searchFilter.sort,
                entries = VenueSortOption.entries,
                onSelected = component::onFilterOptionChanged
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(uiState.filtered) { event ->
                    ListItem(
                        headlineContent = { Text(event.name) },
                        supportingContent = { Text(event.createdAt.toString()) },
                        modifier = Modifier.clickable { component.onShowVenueDetailClicked(event.id) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
