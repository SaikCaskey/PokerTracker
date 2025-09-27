package com.github.saikcaskey.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.settings.data.datastore.SettingsDataStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsDataStoreModule = module {
    single<SettingsDataStore> { SettingsDataStoreImpl(get(), get(), get()) }
    single<DataStore<Preferences>> { androidContext().dataStore }
}

private val Context.dataStore by preferencesDataStore("settings.preferences_pb")
