package com.github.saikcaskey.pokertracker.libs.ui_compose.common.inputform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarBackButton

@Composable
fun InputFormScaffold(
    title: String,
    onBackClicked: (() -> Unit)?,
    onSubmit: () -> Unit,
    isSubmitEnabled: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { onBackClicked?.let { it -> TopBarBackButton(onBackClicked = it) } },
                actions = {
                    TextButton(onClick = onSubmit, enabled = isSubmitEnabled) {
                        Text("Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}
