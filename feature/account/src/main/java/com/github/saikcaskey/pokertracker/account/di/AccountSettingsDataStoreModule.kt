package com.github.saikcaskey.pokertracker.account.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.saikcaskey.pokertracker.domain.datastore.AccountSettingsDataStore
import com.github.saikcaskey.account.data.datastore.AccountSettingsDataStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val accountSettingsDataStoreModule = module {
    single<AccountSettingsDataStore> { AccountSettingsDataStoreImpl(get(), get(), get()) }
    single<DataStore<Preferences>> { androidContext().dataStore }
}

private val Context.dataStore by preferencesDataStore("account_settings.preferences_pb")
