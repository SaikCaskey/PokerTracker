@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.github.saikcaskey.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.domain.components.SettingsFeatureComponent
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
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
        SettingsItemsList(
            value = uiState.value,
            setRandomUserId = {
                component.inputTextValue(UserPreference.UserId, Uuid.random().toString())
            },
            inputToggleValue = { preference, isToggled ->
                component.inputToggleValue(preference, isToggled)
            },
            inputTextValue = { preference, value ->
                component.inputTextValue(preference, value)
            },
            inputNumberValue = { preference, value ->
                component.inputNumberValue(preference, value)
            },
        )
    }
}

@Composable
fun SettingsItemsList(
    value: SettingsFeatureComponent.UiState,
    setRandomUserId: () -> Unit,
    inputToggleValue: (UserPreference<Boolean>, Boolean) -> Unit,
    inputTextValue: (UserPreference<String>, String) -> Unit,
    inputNumberValue: (UserPreference<Int>, Int?) -> Unit,
) {
    LazyColumn {
        items(value.settingsItemsData.items) { item ->
            when (item) {
                is SettingsItem.Check -> SettingsCheckItem(item, inputToggleValue)
                is SettingsItem.Header -> SettingsHeaderItem(item)
                is SettingsItem.Subheader -> SettingsSubheaderItem(item)
                is SettingsItem.Text -> SettingsTextItem(item, setRandomUserId)
                is SettingsItem.TextInput -> SettingsTextInputItem(item, inputTextValue)
                is SettingsItem.Toggle -> SettingsToggleItem(item, inputToggleValue)
                is SettingsItem.NumberInput -> SettingsNumberInputItem(item, inputNumberValue)
            }
            if (item.bottomDivider) HorizontalDivider()
        }
    }
}

@Composable
fun SettingsToggleItem(
    itemData: SettingsItem.Toggle,
    toggleSettingsItem: (UserPreference<Boolean>, Boolean) -> Unit,
) {
    Row {
        ListItem(
            headlineContent = {
                Text(
                    if (itemData.value) "checked" else "not checked",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingContent = {
                ToggleButton(
                    checked = itemData.value,
                    onCheckedChange = {
                        toggleSettingsItem(
                            itemData.linkedUserPreference,
                            !itemData.value
                        )
                    },
                    content = { itemData.title })
            })
    }
}

@Composable
fun SettingsTextInputItem(
    itemData: SettingsItem.TextInput,
    onValueChange: (UserPreference<String>, newValue: String) -> Unit,
) {
    val state = rememberTextFieldState(initialText = itemData.value.orEmpty())

    LaunchedEffect(state.text) {
        snapshotFlow(state::text).collect {
            onValueChange(itemData.linkedUserPreference, it.toString())
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListItem(
            headlineContent = { Text(text = itemData.title ?: "") },
            supportingContent = {
                TextField(
                    state = state,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = MaterialTheme.typography.bodyMedium,
                )
            }
        )
    }
}

@Composable
fun SettingsNumberInputItem(
    itemData: SettingsItem.NumberInput,
    onValueChange: (UserPreference<Int>, newValue: Int?) -> Unit, // The callback to send updates to the Component
) {
    val state = rememberTextFieldState(initialText = itemData.value.toString())

    LaunchedEffect(itemData.value) {
        snapshotFlow(state::text).collect {
            onValueChange(
                itemData.linkedUserPreference,
                it.toString().ifBlank { null }?.toIntOrNull()
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListItem(
            headlineContent = { Text(text = itemData.title ?: "") },
            supportingContent = {
                TextField(
                    state = state,
                    inputTransformation = InputTransformation.maxLength(itemData.maxLength)
                        .then { if (!asCharSequence().isDigitsOnly()) revertAllChanges() },
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle = MaterialTheme.typography.bodyMedium,
                )
            }
        )
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
                text = itemData.title.orEmpty(),
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
                    itemData.title.orEmpty(),
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
            Text(itemData.title.orEmpty(), style = MaterialTheme.typography.headlineLargeEmphasized)
        })
    }
}

@Composable
fun SettingsCheckItem(
    itemData: SettingsItem.Check,
    toggleSettingsItem: (UserPreference<Boolean>, Boolean) -> Unit,
) {
    Row {
        ListItem(
            headlineContent = { Text(if (itemData.value) "checked" else "not checked") },
            leadingContent = {
                Checkbox(
                    checked = itemData.value,
                    onCheckedChange = {
                        toggleSettingsItem(itemData.linkedUserPreference, it)
                    },
                )
            }
        )
    }
}
