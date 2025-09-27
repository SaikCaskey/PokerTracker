package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsAction
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface SettingsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun inputToggleValue(preference: UserPreference<Boolean>, value: Boolean)
    fun inputTextValue(preference: UserPreference<String>, value: String?)
    fun inputNumberValue(preference: UserPreference<Int>, value: Int?)
    fun setRandomUserId()
    fun clearLastSelectedTab()
    fun clearUserId()
    fun addDummyData() 
    fun clearAllData()

    data class UiState(val settingsItemsData: SettingsData = SettingsData()) {
        val settingsItems = buildList {
            // TODO App Section
            add(SettingsItem.Header("App"))
            add(SettingsItem.Subheader("Last Tab Selected:"))
            add(SettingsItem.Text("${settingsItemsData.lastSelectedTab}"))
            add(
                SettingsItem.NumberInput(
                    value = settingsItemsData.lastSelectedTab,
                    linkedUserPreference = UserPreference.LastSelectedTab,
                    maxLength = 100
                )
            )
            add(
                SettingsItem.Text(
                    title = settingsItemsData.lastSelectedTab?.toString(),
                )
            )
            // TODO User Section
            add(SettingsItem.Header("User"))
            add(SettingsItem.Subheader("User Id"))
            add(
                SettingsItem.Text(
                    "${settingsItemsData.userId}",
                    linkedSettingsAction = SettingsAction.SetRandomUserId
                )
            )
            add(
                SettingsItem.TextInput(
                    value = "${settingsItemsData.userId}",
                    linkedUserPreference = UserPreference.UserId,
                )
            )
            // TODO Debug Section
            add(SettingsItem.Header("Debug"))
            add(SettingsItem.Subheader("Is Debug Enabled?"))
            add(SettingsItem.Text("${settingsItemsData.showDebugSettings}"))
            add(
                SettingsItem.Check(
                    settingsItemsData.showDebugSettings == true,
                    linkedUserPreference = UserPreference.ShowDebugSettings
                )
            )
            if (settingsItemsData.showDebugSettings == true) {
                add(
                    SettingsItem.Text(
                        "Clear All Data",
                        linkedSettingsAction = SettingsAction.ClearAllData
                    )
                )
                add(
                    SettingsItem.Text(
                        "Add Dummy Data",
                        linkedSettingsAction = SettingsAction.AddDummyData
                    )
                )
            }
        }
    }
}
