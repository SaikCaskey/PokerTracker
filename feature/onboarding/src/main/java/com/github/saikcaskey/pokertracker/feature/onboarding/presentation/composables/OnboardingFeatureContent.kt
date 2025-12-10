package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.github.saikcaskey.pokertracker.libs.ui_compose.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.User

@Composable
fun OnboardingFeatureContent(
    component: OnboardingFeaturePagerComponent,
) {
    val uiState by component.uiState.collectAsStateWithLifecycle(
        initialValue = OnboardingFeaturePagerComponent.UiState(),
    )

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            IconButton(onClick = component::createInitialUser) {
                FontAwesomeIcons.Solid.User.AsIcon(24.dp, "Go To Account")
            }
            Text("asd User ${uiState.username}")

            Button(component::generateUsername) {
                Text("GenerateUserName")
            }
            Button(component::createInitialUser) {
                Text("Finish onboarding")
            }

            Children(
                stack = component.stack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (it.instance) {
                    is OnboardingFeatureDestination.IntroDestination -> IntroScreen(
                        state = uiState,
                        onNext = component::onClickNext
                    )

                    is OnboardingFeatureDestination.CreateAccountDestination -> CreateAccountScreen(
                        onNext = component::onClickNext,
                        onBack = component::onClickBack,
                        onUsernameChanged = component::onUsernameChanged,
                    )

                    is OnboardingFeatureDestination.InstructionsDestination -> InstructionsScreen(
                        onFinish = component::onClickNext,
                        onBack = component::onClickBack,
                    )
                }
            }
        }
    }
}

@Composable
fun IntroScreen(
    state: OnboardingFeaturePagerComponent.UiState,
    onNext: () -> Unit,
) {

    val localUsernameState = remember(state.username) {
        mutableStateOf(state.username)
    }

    InputFormScaffold(
        title = "Create Account",
        onBackClicked = null,
        onSubmit = {},
        isSubmitEnabled = { }
    ) { }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(64.dp))

        Text(
            text = "IntroScreen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
    }

    Row {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onNext
        ) {
            Text("Start")
        }
    }
}

@Composable
fun CreateAccountScreen(
    onNext: () -> Unit,
    onUsernameChanged: (String) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(64.dp))

        Text(
            text = "Create account",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Row {
            Button(modifier = Modifier.weight(1f), onClick = onNext) {
                Text("Next")
            }
            Button(modifier = Modifier.weight(1f), onClick = onBack) {
                Text("Previous")
            }
        }
    }
}

@Composable
fun InstructionsScreen(onFinish: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(64.dp))

        Text(
            text = "Instructions",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Row {
            Button(onClick = onFinish) {
                Text("Next")
            }
            Button(onClick = onBack) {
                Text("Previous")
            }
        }
    }
}

@Composable
fun ScreenTemplate(
    title: String,
    body: String,
    buttonText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: (() -> Unit)? = null,
    showBack: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(64.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(text = body, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("UI CONTENT GOES HERE", color = MaterialTheme.colorScheme.outline)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBack) {
                OutlinedButton(onClick = onSecondaryClick!!) {
                    Text("Back")
                }
            } else {
                Spacer(Modifier.weight(1f)) // Push Next button to the right if no Back button
            }

            Spacer(Modifier.width(16.dp))

            Button(
                onClick = onPrimaryClick,
                modifier = Modifier.weight(if (showBack) 1f else 0.1f)
            ) {
                Text(buttonText)
            }
        }
    }
}
