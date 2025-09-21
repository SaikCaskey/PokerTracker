@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.github.saikcaskey.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.domain.components.SettingsFeatureComponent
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun SettingsFeatureContent(
    component: SettingsFeatureComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        SettingsItemsList(uiState.value) {
            component.setUserId(Uuid.random())
        }
    }
}

@Composable
fun SettingsItemsList(
    value: SettingsFeatureComponent.UiState,
    setUserId: () -> Unit,
) {
    LazyColumn {
        items(value.settingsItemsData.items) { item ->
            when (item) {
                is SettingsItem.Check -> SettingsCheckItem(item)
                is SettingsItem.Header -> SettingsHeaderItem(item)
                is SettingsItem.Subheader -> SettingsSubheaderItem(item)
                is SettingsItem.Text -> SettingsTextItem(item, setUserId)
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun SettingsTextItem(
    itemData: SettingsItem.Text,
    setUserId: () -> Unit,
) {
    Row {
        ListItem(headlineContent = {
            Text(
                modifier = Modifier.clickable { setUserId() },
                text = itemData.text.orEmpty(),
                style = MaterialTheme.typography.bodyMedium
            )
        })
    }
}

@Composable
fun SettingsSubheaderItem(
    itemData: SettingsItem.Subheader,
) {
    Row {
        ListItem(
            headlineContent = {
                Text(
                    itemData.text.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
        )
    }
}

@Composable
fun SettingsHeaderItem(
    itemData: SettingsItem.Header,
) {
    Row {
        ListItem(headlineContent = {
            Text(itemData.text.orEmpty(), style = MaterialTheme.typography.headlineLargeEmphasized)
        })
    }
}

@Composable
fun SettingsCheckItem(
    itemData: SettingsItem.Check,
) {
    val checkState = remember { mutableStateOf(false) }
    Row {
        ListItem(
            headlineContent = { Text(itemData.text.orEmpty()) },
            leadingContent = {
                Checkbox(
                    checked = checkState.value,
                    onCheckedChange = { checkState.value = it },
                )
            }
        )
    }
}
