package com.github.saikcaskey.pokertracker.app

import android.app.Application
import com.github.saikcaskey.pokertracker.feature.account.di.accountSettingsDataSourceModule
import com.github.saikcaskey.pokertracker.feature.account.di.accountSettingsDataStoreModule
import com.github.saikcaskey.pokertracker.feature.account.di.accountSettingsRepositoryModule
import com.github.saikcaskey.pokertracker.libs.database.di.databaseModule
import com.github.saikcaskey.pokertracker.libs.database.di.sampleDataSeederModule
import com.github.saikcaskey.pokertracker.di.appInfoModule
import com.github.saikcaskey.pokertracker.di.dispatchersProvidersModule
import com.github.saikcaskey.pokertracker.di.navigationModule
import com.github.saikcaskey.pokertracker.di.rootComponentFactoryModule
import com.github.saikcaskey.pokertracker.di.statsComponentFactoryModule
import com.github.saikcaskey.pokertracker.stats.di.statsRepositoryModule
import com.github.saikcaskey.pokertracker.stats.di.userDataSourceModule
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
                    navigationModule,
                    rootComponentFactoryModule,
                    statsComponentFactoryModule,
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
