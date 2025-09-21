package com.github.saikcaskey.settings.domain.datasource.datasource

import com.github.saikcaskey.pokertracker.domain.models.SettingsItemsData
import kotlinx.coroutines.flow.StateFlow

interface SettingsItemsDataSource {
    val state: StateFlow<SettingsItemsData>
}
