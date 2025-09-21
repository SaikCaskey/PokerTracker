package com.github.saikcaskey.settings.data.repository

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
}
