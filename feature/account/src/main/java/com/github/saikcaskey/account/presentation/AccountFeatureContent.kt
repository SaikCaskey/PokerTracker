@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.github.saikcaskey.account.presentation

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
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
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
import com.github.saikcaskey.account.domain.model.AccountSettingsAction
import com.github.saikcaskey.account.domain.model.AccountSettingsItem
import com.github.saikcaskey.account.domain.model.AccountSettingsItem.*
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.*
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputSearchableDropdownField
import kotlinx.coroutines.flow.collectLatest
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AccountFeatureContent(
    component: AccountFeatureComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle()

    AccountSettingsScreenContent(
        uiState = uiState.value,
        clearUserId = component::clearUserId,
        clearDefaultBuyIn = component::clearDefaultBuyIn,
        setRandomUserId = component::setRandomUserId,
        addDummyData = component::seed,
        clearAllData = component::clearAllData,
        updatePreferenceValue = component::updatePreferenceValue
    )
}

@Composable
fun AccountSettingsScreenContent(
    uiState: AccountFeatureComponent.UiState,
    clearUserId: () -> Unit,
    clearDefaultBuyIn: () -> Unit,
    setRandomUserId: () -> Unit,
    addDummyData: (AccountSettingsAction.SeedData) -> Unit,
    clearAllData: () -> Unit,
    updatePreferenceValue: (UserPreference<*>, Any?) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SettingsItemsList(
            settingItems = uiState.accountSettingsItems,
            updatePreferenceValue = updatePreferenceValue,
            onItemPressed = { settingsItem ->
                val action = settingsItem.linkedAction
                when (action) {
                    AccountSettingsAction.ClearDefaultBuyIn -> clearDefaultBuyIn()
                    AccountSettingsAction.ClearUserId -> clearUserId()
                    AccountSettingsAction.SetRandomUserId -> setRandomUserId()
                    is AccountSettingsAction.SeedData.SmokeTest -> addDummyData(action)
                    is AccountSettingsAction.SeedData.BadDay -> addDummyData(action)
                    is AccountSettingsAction.SeedData.GoodDay -> addDummyData(action)
                    is AccountSettingsAction.SeedData.User -> addDummyData(action)
                    AccountSettingsAction.ClearAllData -> clearAllData()
                    null -> Unit
                }
            },
        )
    }
}

@Composable
fun SettingsItemsList(
    settingItems: List<AccountSettingsItem>,
    onItemPressed: ((AccountSettingsItem) -> Unit)?,
    updatePreferenceValue: (UserPreference<*>, Any?) -> Unit,
) {
    LazyColumn {
        items(settingItems) { item ->
            when (item) {
                is Button -> SettingsButtonItem(item, onItemPressed)
                is Check -> SettingsCheckItem(item, updatePreferenceValue)
                is Header -> SettingsHeaderItem(item)
                is InfoText -> SettingsTextItem(item) { onItemPressed?.invoke(item) }
                is Toggle -> SettingsToggleItem(item, updatePreferenceValue)
                is IntegerInput -> SettingsIntInputItem(item, updatePreferenceValue)
                is LongInput -> SettingsLongInputItem(item, updatePreferenceValue, onItemPressed)
                is TextInput -> {
                    SettingsTextInputItem(item, updatePreferenceValue, onItemPressed)
                }

                is DropdownInput<*> -> {
                    when (item.linkedPreference) {
                        DefaultBuyIn -> SettingsDropdownInputItem(
                            itemData = item,
                            onItemSelected = { updatePreferenceValue(item.linkedPreference, it) },
                            label = "DefaultBuyIn",
                            itemToString = Any?::toString,
                            onAddEventClicked = {},
                        )

                        ShowAdvancedSettings -> SettingsDropdownInputItem(
                            itemData = item,
                            onItemSelected = { updatePreferenceValue(item.linkedPreference, it) },
                            label = "ShowAdvancedSettings",
                            itemToString = Any?::toString,
                            onAddEventClicked = {},
                        )

                        UserId -> SettingsDropdownInputItem(
                            itemData = item,
                            onItemSelected = { updatePreferenceValue(item.linkedPreference, it) },
                            label = "UserId",
                            itemToString = Any?::toString,
                            onAddEventClicked = {},
                        )
                    }
                }
            }
            if (item.bottomDivider) HorizontalDivider()
        }
    }
}

@Composable
fun <T> SettingsDropdownInputItem(
    label: String = "",
    itemData: DropdownInput<T>,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit,
    onAddEventClicked: () -> Unit,
) {
    InputSearchableDropdownField(
        label = label,
        items = itemData.suggestions,
        selectedItem = itemData.value,
        filterItems = false,
        itemToString = itemToString,
        onItemSelected = onItemSelected,
        onAddNewItemClicked = onAddEventClicked
    )
}

@Composable
fun SettingsButtonItem(
    itemData: Button,
    onPressedSettingsItem: ((AccountSettingsItem) -> Unit)?,
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
                itemData.linkedPreference,
                !itemData.value
            )
        },
        content = { itemData.title })
}

@Composable
fun SettingsTextInputItem(
    itemData: TextInput,
    onValueChange: (UserPreference<String>, newValue: String) -> Unit,
    onItemPressed: ((AccountSettingsItem) -> Unit)?,
) {
    val state = TextFieldState(itemData.value.orEmpty())
    LaunchedEffect(itemData.value) {
        snapshotFlow(state::text).collect {
            onValueChange(itemData.linkedPreference, it.toString())
        }
    }

    TextField(
        label = {
            Text(
                modifier = Modifier.clickable { onItemPressed?.invoke(itemData) },
                text = itemData.title.orEmpty()
            )
        },
        state = state,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun SettingsIntInputItem(
    itemData: IntegerInput,
    onValueChange: (UserPreference<Int>, newValue: Int?) -> Unit,
) {
    val state = rememberTextFieldState(initialText = itemData.value?.toString().orEmpty())

    LaunchedEffect(itemData.value) {
        snapshotFlow(state::text).collect {
            onValueChange(
                itemData.linkedPreference,
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
fun SettingsLongInputItem(
    itemData: LongInput,
    onValueChange: (UserPreference<Long>, newValue: Long?) -> Unit,
    onItemPressed: ((AccountSettingsItem) -> Unit)?,
) {
    val state = rememberTextFieldState(initialText = itemData.value?.toString().orEmpty())
    state.setTextAndPlaceCursorAtEnd(itemData.value?.toString().orEmpty())

    LaunchedEffect(itemData.value) {
        snapshotFlow(state::text).collectLatest {
            onValueChange(
                itemData.linkedPreference,
                it.toString().ifBlank { null }?.toLongOrNull()
            )
        }
    }

    TextField(
        state = state,
        label = {
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = { onItemPressed?.invoke(itemData) }
                ),
                text = itemData.title.orEmpty()
            )
        },
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
                toggleSettingsItem(itemData.linkedPreference, isChecked)
            },
        )
    }
}
