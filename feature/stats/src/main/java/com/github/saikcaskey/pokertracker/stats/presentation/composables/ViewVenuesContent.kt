package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopAppBarItemViewer
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownSimple
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewVenuesComponent

@Composable
fun ViewVenuesContent(component: ViewVenuesComponent) {
    val uiState by component.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBarItemViewer(
                title = "All Venues",
                onBackClicked = component::onBackClicked,
                onShowInsertItemClicked = component::onShowInsertVenueClicked,
                onDeleteAllItemsClicked = component::onDeleteAllVenuesClicked,
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchFilter.query.orEmpty(),
                onValueChange = component::onSearchQueryChanged,
                label = { Text("Search Venues") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            InputDropdownSimple(
                selected = uiState.searchFilter.sort,
                entries = ViewVenuesComponent.VenueSortOption.entries,
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
