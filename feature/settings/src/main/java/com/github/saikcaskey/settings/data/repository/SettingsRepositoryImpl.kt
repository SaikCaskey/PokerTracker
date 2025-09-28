package com.github.saikcaskey.settings.data.repository

import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.settings.domain.datasource.SettingsDataSource
import com.github.saikcaskey.pokertracker.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource,
) : SettingsRepository {

    override val state = settingsDataSource.state

    override fun <R : Any?> setUserPreference(
        preference: UserPreference<R>,
        value: R?,
    ) {
        when (preference) {
            is UserPreference.ShowDebugSettings -> settingsDataSource.setShowDebugSettings(value as Boolean)
            is UserPreference.DefaultBuyIn -> settingsDataSource.setDefaultBuyIn(value as? Int)
            is UserPreference.UserId -> settingsDataSource.setUserId(value as? String)
        }
    }
}
