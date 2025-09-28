package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.AddDummyData
import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.ClearAllData
import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.SetRandomUserId
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.DefaultBuyIn
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.ShowAdvancedSettings
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.UserId
import kotlinx.coroutines.flow.StateFlow

interface SettingsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun updatePreferenceValue(preference: UserPreference<*>, value: Any?)

    fun setRandomUserId()
    fun clearDefaultBuyIn()
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
                    linkedUserPreference = DefaultBuyIn,
                    maxLength = 100
                )
            )
            add(SettingsItem.Header("User"))
            add(
                SettingsItem.TextInput(
                    title = "User Id:",
                    value = "${settingsData.userId}",
                    linkedUserPreference = UserId,
                    linkedSettingsAction = SetRandomUserId
                )
            )
            add(SettingsItem.Header("Debug"))
            add(SettingsItem.InfoText("Hash: ${settingsData.gitCommitHash}"))
            add(SettingsItem.InfoText("VersionName: ${settingsData.versionName}"))
            add(SettingsItem.InfoText("VersionCode: ${settingsData.versionCode}"))
            add(SettingsItem.InfoText("BuildType: ${settingsData.buildType}"))
            add(SettingsItem.InfoText("IsProd: ${settingsData.isProd}"))
            add(
                SettingsItem.InfoText(
                    "Advanced settings ${if (settingsData.showAdvancedSettings) "" else "NOT "}enabled",
                    bottomDivider = true
                )
            )
            add(
                SettingsItem.Check(
                    value = settingsData.showAdvancedSettings,
                    linkedUserPreference = ShowAdvancedSettings
                )
            )
            if (settingsData.showAdvancedSettings) {
                add(
                    SettingsItem.Button(
                        title = "Clear All Data",
                        linkedSettingsAction = ClearAllData
                    )
                )
                add(
                    SettingsItem.Button(
                        title = "Add Dummy Data",
                        linkedSettingsAction = AddDummyData
                    )
                )
            }
        }
    }
}
