package com.github.saikcaskey.pokertracker.app

import android.app.Application
import com.github.saikcaskey.pokertracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokerTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokerTrackerApp)
            modules(appModule())
        }
    }
}