package com.github.saikcaskey.account.presentation

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.account.domain.model.AccountSettingsItem
import com.github.saikcaskey.account.domain.model.AccountSettingsAction
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.presentation.MainPagerPageComponent
import kotlinx.coroutines.flow.StateFlow

interface AccountFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun updatePreferenceValue(preference: UserPreference<*>, value: Any?)

    fun setRandomUserId()
    fun clearDefaultBuyIn()
    fun clearUserId()
    fun seed(action: AccountSettingsAction.SeedData)
    fun clearAllData()

    data class UiState(
        val accountSettingsData: AccountSettingsData = AccountSettingsData(),
        val userIdSuggestionsData: List<Long> = emptyList(),
    ) {
        val accountSettingsItems = buildList {
            add(AccountSettingsItem.Header("General"))
            add(
                AccountSettingsItem.IntegerInput(
                    title = "Default Buy in:",
                    value = accountSettingsData.defaultBuyIn,
                    linkedPreference = UserPreference.DefaultBuyIn,
                    maxLength = 100
                )
            )
            add(AccountSettingsItem.Header("User"))
            add(
                AccountSettingsItem.DropdownInput(
                    title = "User Id:",
                    value = accountSettingsData.userId,
                    linkedPreference = UserPreference.UserId,
                    linkedAction = AccountSettingsAction.SetRandomUserId,
                    suggestions = userIdSuggestionsData
                )
            )
            add(AccountSettingsItem.Header("Debug"))
            add(AccountSettingsItem.InfoText("UserId: ${accountSettingsData.userId}"))
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
                    linkedPreference = UserPreference.ShowAdvancedSettings
                )
            )
            if (accountSettingsData.showAdvancedSettings) {
                add(
                    AccountSettingsItem.Button(
                        title = "Clear All Data",
                        linkedAction = AccountSettingsAction.ClearAllData
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Seed - Add User",
                        linkedAction = AccountSettingsAction.SeedData.User
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Seed - SmokeTest",
                        linkedAction = AccountSettingsAction.SeedData.SmokeTest
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Seed - GoodDay",
                        linkedAction = AccountSettingsAction.SeedData.GoodDay
                    )
                )
                add(
                    AccountSettingsItem.Button(
                        title = "Seed - BadDay",
                        linkedAction = AccountSettingsAction.SeedData.BadDay
                    )
                )
            }
        }
    }
}
