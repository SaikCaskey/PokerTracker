package com.github.saikcaskey.account.data.repository

import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.account.domain.datasource.AccountSettingsDataSource
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository

class AccountSettingsRepositoryImpl(
    private val accountSettingsDataSource: AccountSettingsDataSource,
) : AccountSettingsRepository {

    override val state = accountSettingsDataSource.state

    override fun <R : Any?> setUserPreference(
        preference: UserPreference<R>,
        value: R?,
    ) {
        when (preference) {
            is UserPreference.ShowAdvancedSettings -> accountSettingsDataSource.setShowAdvancedSettings(value as Boolean)
            is UserPreference.DefaultBuyIn -> accountSettingsDataSource.setDefaultBuyIn(value as? Int)
            is UserPreference.UserId -> accountSettingsDataSource.setUserId(value as? String)
        }
    }
}
