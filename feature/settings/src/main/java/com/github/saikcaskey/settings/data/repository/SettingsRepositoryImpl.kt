package com.github.saikcaskey.settings.data.repository

import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.settings.domain.datasource.datasource.SettingsItemsDataSource
import com.github.saikcaskey.settings.domain.datasource.datasource.UserSettingsDataSource
import com.github.saikcaskey.pokertracker.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val userSettingsDataSource: UserSettingsDataSource,
    settingsItemsDataSource: SettingsItemsDataSource,
) : SettingsRepository {

    override val state = settingsItemsDataSource.state

    override fun setUserId(userId: String?) {
        return userSettingsDataSource.setUserId(userId)
    }

    override fun <R: Any?> setUserPreference(
        preference: UserPreference<R>,
        value: R?,
    ) {
        when (preference) {
            is UserPreference.IsDebug -> userSettingsDataSource.setIsDebug(value as Boolean)
            is UserPreference.LastSelectedTab -> userSettingsDataSource.setLastSelectedTab(value as? Int)
            is UserPreference.UserId -> userSettingsDataSource.setUserId(value as? String)
        }
    }
}
