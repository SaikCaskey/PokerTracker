package com.github.saikcaskey.settings.di

import com.github.saikcaskey.pokertracker.domain.repository.SettingsRepository
import com.github.saikcaskey.settings.data.repository.SettingsRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val settingsRepositoryModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}

object SettingsRepositoryProvider : KoinComponent {
    fun provide(): SettingsRepository = get()
}
