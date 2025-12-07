package com.github.saikcaskey.pokertracker.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.github.saikcaskey.pokertracker.app.di.RootComponentFactoryProvider
import com.github.saikcaskey.pokertracker.app.di.RootNavigatorProvider
import com.github.saikcaskey.pokertracker.app.presentation.components.RootComponentImpl
import com.github.saikcaskey.pokertracker.app.presentation.composables.RootContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val root = RootComponentImpl(
            componentContext = defaultComponentContext(),
            rootNavigator = RootNavigatorProvider.provide(),
            componentFactory = RootComponentFactoryProvider.provide(),
        )

        setContent {
            RootContent(component = root)
        }
    }
}
