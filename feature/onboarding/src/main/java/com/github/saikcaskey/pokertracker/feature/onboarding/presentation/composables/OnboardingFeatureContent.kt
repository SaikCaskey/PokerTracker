package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.material3.OutlinedTextField
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
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Dice

@Composable
fun OnboardingFeatureContent(
    component: OnboardingFeaturePagerComponent,
) {
    val uiState by component.uiState.collectAsStateWithLifecycle(
        initialValue = OnboardingFeaturePagerComponent.UiState(),
    )

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
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
                    is OnboardingFeatureDestination.IntroDestination -> OnboardingPageIntro(
                        onNext = component::onClickNext,
                    )

                    is OnboardingFeatureDestination.CreateAccountDestination -> OnboardingPageCreateAccount(
                        state = uiState,
                        onNext = component::onClickNext,
                        onBack = component::onClickBack,
                        onUsernameChanged = component::onUsernameChanged,
                        onGenerateRandomUsernameClicked = component::generateUsername,
                    )

                    is OnboardingFeatureDestination.InstructionsDestination -> OnboardingPageInstructions(
                        onNext = component::onClickNext,
                        onBack = component::onClickBack,
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPageInstructions(
    onNext: () -> Unit,
    onBack: (() -> Unit)?,
) {
    OnboardingPagerPage(
        onNext = onNext,
        onBack = onBack,
        title = "Instructions",
        body = "Track your poker cash flow by adding individual expenses, or attaching expenses to an event. Start by adding a Venue, or some Expenses.",
        buttonText = "Done",
    )
}

@Composable
fun OnboardingPageIntro(
    onNext: () -> Unit,
) {
    OnboardingPagerPage(
        onNext = onNext,
        title = "Intro",
        body = "Intro",
        buttonText = "Next"
    )
}

@Composable
fun OnboardingPageCreateAccount(
    state: OnboardingFeaturePagerComponent.UiState,
    modifier: Modifier = Modifier,
    onUsernameChanged: (String) -> Unit,
    onGenerateRandomUsernameClicked: () -> Unit,
    onNext: () -> Unit,
    onBack: (() -> Unit)?,
) {
    OnboardingPagerPage(
        title = "Create Account",
        body = "Create an account by setting a username",
        buttonText = "Next",
        modifier = modifier,
        onNext = onNext,
        onBack = onBack
    ) {
        CreateAccountInputField(
            state = state,
            onUsernameChanged = onUsernameChanged,
            onGenerateRandomUsernameClicked = onGenerateRandomUsernameClicked
        )
    }
}

@Composable
fun CreateAccountInputField(
    state: OnboardingFeaturePagerComponent.UiState,
    onUsernameChanged: (String) -> Unit,
    onGenerateRandomUsernameClicked: () -> Unit
) {
    val localUsernameState = remember(state.username) {
        mutableStateOf(state.username)
    }
    Row {
        OutlinedTextField(
            value = localUsernameState.value.orEmpty(),
            onValueChange = { newValue ->
                localUsernameState.value = newValue
                onUsernameChanged(newValue)
            },
            label = { Text("Username") },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onGenerateRandomUsernameClicked,
        ){
            FontAwesomeIcons.Solid.Dice.AsIcon(
                height = 24.dp,
                contentDescription = "Random username"
            )
        }
    }
}

@Composable
fun OnboardingPagerPage(
    title: String,
    body: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBack: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
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
            content()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onBack != null) {
                OutlinedButton(onClick = onBack) {
                    Text("Back")
                }
            }
            Spacer(Modifier.width(16.dp))

            Button(onClick = onNext) {
                Text(buttonText)
            }
        }
    }
}
