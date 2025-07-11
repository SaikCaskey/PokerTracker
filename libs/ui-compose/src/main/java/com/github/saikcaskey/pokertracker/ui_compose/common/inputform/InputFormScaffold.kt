package com.github.saikcaskey.pokertracker.ui_compose.common.inputform

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarBackButton

@Composable
fun InputFormScaffold(
    title: String,
    onBackClicked: () -> Unit,
    onSubmit: () -> Unit,
    isSubmitEnabled: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { TopBarBackButton(onBackClicked = onBackClicked) },
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
