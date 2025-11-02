package com.github.saikcaskey.pokertracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.github.saikcaskey.pokertracker.di.ComponentFactoryProvider
import com.github.saikcaskey.pokertracker.di.RootNavigatorProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val root = RootComponentImpl(
            componentContext = defaultComponentContext(),
            rootNavigator = RootNavigatorProvider.provide(),
            componentFactory = ComponentFactoryProvider.provide(),
        )

        setContent {
            RootContent(component = root)
        }
    }
}
