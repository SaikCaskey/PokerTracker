package com.github.saikcaskey.account.data.repository

import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.domain.datasource.AccountSettingsDataSource
import com.github.saikcaskey.pokertracker.domain.models.UserPreference

class AccountSettingsRepositoryImpl(
    private val accountSettingsDataSource: AccountSettingsDataSource,
) : AccountSettingsRepository {

    override val state = accountSettingsDataSource.state

    override fun setUserPreference(preference: UserPreference<*>, value: Any?) {
        accountSettingsDataSource.setUserPreference(preference, value)
    }
}
