package com.github.saikcaskey.pokertracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.github.saikcaskey.data.di.CoroutineDispatchersProvider
import com.github.saikcaskey.stats.di.EventRepositoryProvider
import com.github.saikcaskey.stats.di.ExpenseRepositoryProvider
import com.github.saikcaskey.stats.di.VenueRepositoryProvider
import com.github.saikcaskey.pokertracker.presentation.root.RootComponentImpl
import com.github.saikcaskey.pokertracker.presentation.root.RootContent
import com.github.saikcaskey.account.di.AccountSettingsRepositoryProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val root = RootComponentImpl(
            componentContext = defaultComponentContext(),
            dispatchers = CoroutineDispatchersProvider.provide(),
            eventRepository = EventRepositoryProvider.provide(),
            venueRepository = VenueRepositoryProvider.provide(),
            expenseRepository = ExpenseRepositoryProvider.provide(),
            accountSettingsRepository = AccountSettingsRepositoryProvider.provide(),
        )

        setContent {
            RootContent(component = root)
        }
    }
}
