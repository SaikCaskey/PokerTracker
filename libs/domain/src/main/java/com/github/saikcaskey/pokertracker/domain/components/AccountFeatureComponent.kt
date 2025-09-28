package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.AddDummyData
import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.ClearAllData
import com.github.saikcaskey.pokertracker.domain.models.SettingsAction.SetRandomUserId
import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsItem
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.DefaultBuyIn
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.ShowAdvancedSettings
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.UserId
import kotlinx.coroutines.flow.StateFlow

interface AccountFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun updatePreferenceValue(preference: UserPreference<*>, value: Any?)

    fun setRandomUserId()
    fun clearDefaultBuyIn()
    fun clearUserId()
    fun addDummyData()
    fun clearAllData()

    data class UiState(val accountSettingsData: AccountSettingsData = AccountSettingsData()) {
        val accountSettingsItems = buildList {
            add(AccountSettingsItem.Header("General"))
            add(
                AccountSettingsItem.NumberInput(
                    title = "Default Buy in:",
                    value = accountSettingsData.defaultBuyIn,
                    linkedUserPreference = DefaultBuyIn,
                    maxLength = 100
                )
            )
            add(AccountSettingsItem.Header("User"))
            add(
                AccountSettingsItem.TextInput(
                    title = "User Id:",
                    value = "${accountSettingsData.userId}",
                    linkedUserPreference = UserId,
                    linkedSettingsAction = SetRandomUserId
                )
            )
            add(AccountSettingsItem.Header("Debug"))
            add(AccountSettingsItem.InfoText("Hash: ${accountSettingsData.gitCommitHash}"))
            add(AccountSettingsItem.InfoText("VersionName: ${accountSettingsData.versionName}"))
            add(AccountSettingsItem.InfoText("VersionCode: ${accountSettingsData.versionCode}"))
            add(AccountSettingsItem.InfoText("BuildType: ${accountSettingsData.buildType}"))
            add(AccountSettingsItem.InfoText("IsProd: ${accountSettingsData.isProd}"))
            add(
                AccountSettingsItem.InfoText(
                    "Advanced settings ${if (accountSettingsData.showAdvancedSettings) "" else "NOT "}enabled",
                    bottomDivider = true
                )
            )
            add(
                AccountSettingsItem.Check(
                    value = accountSettingsData.showAdvancedSettings,
                    linkedUserPreference = ShowAdvancedSettings
                )
            )
            if (accountSettingsData.showAdvancedSettings) {
                add(
                    AccountSettingsItem.Button(
                        title = "Clear All Data",
                        linkedSettingsAction = ClearAllData
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Add Dummy Data",
                        linkedSettingsAction = AddDummyData
                    )
                )
            }
        }
    }
}
