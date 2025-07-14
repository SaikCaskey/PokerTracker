package com.github.saikcaskey.pokertracker.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.*
import compose.icons.fontawesomeicons.regular.Calendar
import compose.icons.fontawesomeicons.regular.ChartBar

@Composable
fun MainPagerBottomAppBar(
    selectPage: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
        tonalElevation = 12.dp,
        actions = {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { selectPage(0) }) {
                Icon(
                    modifier = Modifier.height(48.dp),
                    imageVector = FontAwesomeIcons.Regular.ChartBar,
                    contentDescription = ""
                )
            }

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { selectPage(1) }) {
                Icon(
                    modifier = Modifier.height(48.dp),
                    imageVector = FontAwesomeIcons.Regular.Calendar,
                    contentDescription = ""
                )
            }
        },
    )
}
