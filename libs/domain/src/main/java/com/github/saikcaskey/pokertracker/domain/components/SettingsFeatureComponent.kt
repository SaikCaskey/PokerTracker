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

    data class UiState(val settingsData: SettingsData = SettingsData()) {
        val settingsItems = buildList {
            add(SettingsItem.Header("General"))
            add(
                SettingsItem.NumberInput(
                    title = "Default Buy in:",
                    value = settingsData.defaultBuyIn,
                    linkedUserPreference = LastSelectedTab,
                    maxLength = 100
                )
            )
            add(SettingsItem.Header("User"))
            add(
                SettingsItem.TextInput(
                    title = "User Id:",
                    value = "${settingsData.userId}",
                    linkedUserPreference = UserId,
                    linkedSettingsAction = SettingsAction.SetRandomUserId
                )
            )
            add(SettingsItem.Header("Debug"))
            add(
                SettingsItem.Subheader(
                    "Debug settings ${if (settingsData.showDebugSettings) "" else "NOT "}enabled"
                )
            )
            add(
                SettingsItem.Check(
                    value = settingsData.showDebugSettings,
                    linkedUserPreference = ShowDebugSettings
                )
            )
            if (settingsData.showDebugSettings) {
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
