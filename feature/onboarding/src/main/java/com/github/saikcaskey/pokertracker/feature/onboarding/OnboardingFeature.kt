package com.github.saikcaskey.pokertracker.feature.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.feature.onboarding.composables.OnboardingFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.User

@Composable
fun OnboardingFeatureContent(
    component: OnboardingFeatureComponent,
) {
    val uiState by component.uiState.collectAsStateWithLifecycle(
        initialValue = OnboardingFeatureComponent.UiState(),
    )

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            IconButton(onClick = component::finishOnboarding) {
                FontAwesomeIcons.Solid.User.AsIcon(24.dp, "Go To Account")
            }
            Text("asd User ${uiState.generatedUsername}")

            Button(component::generateUsername) {
                Text("GenerateUserName")
            }
            Button(component::finishOnboarding) {
                Text("Finish onboarding")
            }
        }
    }
}
