package com.github.saikcaskey.pokertracker.app

import android.app.Application
import com.github.saikcaskey.account.di.accountSettingsDataStoreModule
import com.github.saikcaskey.account.di.accountSettingsDataSourceModule
import com.github.saikcaskey.account.di.accountSettingsRepositoryModule
import com.github.saikcaskey.database.di.databaseModule
import com.github.saikcaskey.database.di.sampleDataSeederModule
import com.github.saikcaskey.pokertracker.di.dispatchersProvidersModule
import com.github.saikcaskey.pokertracker.di.appInfoModule
import com.github.saikcaskey.stats.di.statsRepositoryModule
import com.github.saikcaskey.stats.di.userDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokerTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokerTrackerApp)
            modules(
                listOf(
                    appInfoModule,
                    databaseModule,
                    dispatchersProvidersModule,
                    statsRepositoryModule,
                    userDataSourceModule,
                    sampleDataSeederModule,
                    accountSettingsRepositoryModule,
                    accountSettingsDataSourceModule,
                    accountSettingsDataStoreModule,
                )
            )
        }
    }
}
