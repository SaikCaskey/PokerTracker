package com.github.saikcaskey.account.presentation

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.account.domain.model.AccountSettingsItem
import com.github.saikcaskey.account.domain.model.SettingsAction
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.presentation.MainPagerPageComponent
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
                    linkedUserPreference = UserPreference.DefaultBuyIn,
                    maxLength = 100
                )
            )
            add(AccountSettingsItem.Header("User"))
            add(
                AccountSettingsItem.TextInput(
                    title = "User Id:",
                    value = "${accountSettingsData.userId}",
                    linkedUserPreference = UserPreference.UserId,
                    linkedSettingsAction = SettingsAction.SetRandomUserId
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
                    linkedUserPreference = UserPreference.ShowAdvancedSettings
                )
            )
            if (accountSettingsData.showAdvancedSettings) {
                add(
                    AccountSettingsItem.Button(
                        title = "Clear All Data",
                        linkedSettingsAction = SettingsAction.ClearAllData
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Add Dummy Data",
                        linkedSettingsAction = SettingsAction.AddDummyData
                    )
                )
            }
        }
    }
}
