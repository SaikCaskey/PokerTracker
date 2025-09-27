package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsAction
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.*
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
            add(SettingsItem.Header("General"))
            add(
                SettingsItem.NumberInput(
                    title = "Default Buy in:",
                    value = settingsItemsData.defaultBuyIn,
                    linkedUserPreference = LastSelectedTab,
                    maxLength = 100
                )
            )

            add(SettingsItem.Header("User"))
            add(
                SettingsItem.TextInput(
                    title = "User Id:",
                    value = "${settingsItemsData.userId}",
                    linkedUserPreference = UserId,
                    linkedSettingsAction = SettingsAction.SetRandomUserId

                )
            )
            // TODO Debug Section
            add(SettingsItem.Header("Debug"))
            add(
                SettingsItem.Subheader(
                    "Debug settings ${if (settingsItemsData.showDebugSettings) "" else "NOT"} enabled"
                )
            )
            add(
                SettingsItem.Check(
                    value = settingsItemsData.showDebugSettings,
                    linkedUserPreference = ShowDebugSettings
                )
            )
            if (settingsItemsData.showDebugSettings) {
                add(
                    SettingsItem.Text(
                        title = "Clear All Data",
                        linkedSettingsAction = SettingsAction.ClearAllData
                    )
                )
                add(
                    SettingsItem.Text(
                        title = "Add Dummy Data",
                        linkedSettingsAction = SettingsAction.AddDummyData
                    )
                )
            }
        }
    }
}
