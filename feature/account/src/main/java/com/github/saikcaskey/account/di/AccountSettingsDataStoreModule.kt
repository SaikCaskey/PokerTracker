package com.github.saikcaskey.account.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.account.data.datastore.SettingsDataStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val accountSettingsDataStoreModule = module {
    single<SettingsDataStore> { SettingsDataStoreImpl(get(), get(), get()) }
    single<DataStore<Preferences>> { androidContext().dataStore }
}

private val Context.dataStore by preferencesDataStore("account_settings.preferences_pb")
