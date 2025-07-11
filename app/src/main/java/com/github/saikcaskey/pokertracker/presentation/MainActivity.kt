package com.github.saikcaskey.pokertracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.github.saikcaskey.pokertracker.di.CoroutineDispatchersProvider
import com.github.saikcaskey.pokertracker.di.EventRepositoryProvider
import com.github.saikcaskey.pokertracker.di.ExpenseRepositoryProvider
import com.github.saikcaskey.pokertracker.di.VenueRepositoryProvider
import com.github.saikcaskey.pokertracker.ui_compose.components.root.RootContent
import com.github.saikcaskey.pokertracker.presentation.root.DefaultRootComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            dispatchers = CoroutineDispatchersProvider.provide(),
            eventRepository = EventRepositoryProvider.provide(),
            venueRepository = VenueRepositoryProvider.provide(),
            expenseRepository = ExpenseRepositoryProvider.provide(),
        )

        setContent {
            RootContent(component = root)
        }
    }
}
