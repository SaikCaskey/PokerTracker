package com.github.saikcaskey.pokertracker.feature.account.di

import com.github.saikcaskey.pokertracker.feature.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.feature.account.data.repository.AccountSettingsRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val accountSettingsRepositoryModule = module {
    single<AccountSettingsRepository> { AccountSettingsRepositoryImpl(get()) }
}

object AccountSettingsRepositoryProvider : KoinComponent {
    fun provide(): AccountSettingsRepository = get()
}
