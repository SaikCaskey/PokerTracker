@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.github.saikcaskey.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.domain.components.SettingsFeatureComponent
import com.github.saikcaskey.pokertracker.domain.models.SettingsAction
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem.*
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun SettingsFeatureContent(
    component: SettingsFeatureComponent,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = component.uiState.collectAsStateWithLifecycle()
        SettingsItemsList(
            settingItems = uiState.value.settingsItems,
            onItemPressed = { settingsItem ->
                when (settingsItem.linkedSettingsAction) {
                    SettingsAction.ClearDefaultBuyIn -> component.clearDefaultBuyIn()
                    SettingsAction.ClearUserId -> component.clearUserId()
                    SettingsAction.SetRandomUserId -> component.setRandomUserId()
                    SettingsAction.AddDummyData -> component.addDummyData()
                    SettingsAction.ClearAllData -> component.clearAllData()
                    null -> {}
                }
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
    settingItems: List<SettingsItem>,
    onItemPressed: ((SettingsItem) -> Unit)?,
    inputToggleValue: (UserPreference<Boolean>, Boolean) -> Unit,
    inputTextValue: (UserPreference<String>, String) -> Unit,
    inputNumberValue: (UserPreference<Int>, Int?) -> Unit,
) {
    LazyColumn {
        items(settingItems) { item ->
            when (item) {
                is Button -> SettingsButtonItem(item, onItemPressed)
                is Check -> SettingsCheckItem(item, inputToggleValue)
                is Header -> SettingsHeaderItem(item)
                is InfoText -> SettingsTextItem(item) { onItemPressed?.invoke(item) }
                is Toggle -> SettingsToggleItem(item, inputToggleValue)
                is NumberInput -> SettingsNumberInputItem(item, inputNumberValue)
                is TextInput -> {
                    SettingsTextInputItem(item, inputTextValue, onItemPressed)
                }
            }
            if (item.bottomDivider) HorizontalDivider()
        }
    }
}

@Composable
fun SettingsButtonItem(
    itemData: Button,
    onPressedSettingsItem: ((SettingsItem) -> Unit)?,
) {
    Button(onClick = { onPressedSettingsItem?.invoke(itemData) }) {
        Text(itemData.title.orEmpty())
    }
}

@Composable
fun SettingsToggleItem(
    itemData: Toggle,
    toggleSettingsItem: (UserPreference<Boolean>, Boolean) -> Unit,
) {
    ToggleButton(
        checked = itemData.value,
        onCheckedChange = {
            toggleSettingsItem(
                itemData.linkedUserPreference,
                !itemData.value
            )
        },
        content = { itemData.title })
}

@Composable
fun SettingsTextInputItem(
    itemData: TextInput,
    onValueChange: (UserPreference<String>, newValue: String) -> Unit,
    onPressedSettingsItem: ((SettingsItem) -> Unit)?,
) {
    val state = TextFieldState(itemData.value.orEmpty())
    LaunchedEffect(itemData.value) {
        snapshotFlow(state::text).collect {
            onValueChange(itemData.linkedUserPreference, it.toString())
        }
    }

    TextField(
        modifier = Modifier.clickable {
            onPressedSettingsItem?.invoke(itemData)
        },
        label = { Text(itemData.title.orEmpty()) },
        state = state,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun SettingsNumberInputItem(
    itemData: NumberInput,
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

    TextField(
        state = state,
        label = { Text(itemData.title.orEmpty()) },
        inputTransformation = InputTransformation.maxLength(itemData.maxLength)
            .then { if (!asCharSequence().isDigitsOnly()) revertAllChanges() },
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun SettingsTextItem(
    itemData: InfoText,
    onPressed: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.clickable(onClick = { onPressed?.invoke() }),
        text = itemData.title.orEmpty(),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun SettingsHeaderItem(
    itemData: Header,
) {
    Text(itemData.title.orEmpty(), style = MaterialTheme.typography.headlineLargeEmphasized)
}

@Composable
fun SettingsCheckItem(
    itemData: Check,
    toggleSettingsItem: (UserPreference<Boolean>, Boolean) -> Unit,
) {
    Row {
        Text(itemData.title.orEmpty(), style = MaterialTheme.typography.bodyMedium)
        Checkbox(
            checked = itemData.value,
            onCheckedChange = { isChecked ->
                toggleSettingsItem(itemData.linkedUserPreference, isChecked)
            },
        )
    }
}
