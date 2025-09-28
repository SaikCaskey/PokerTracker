package com.github.saikcaskey.pokertracker.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Calendar
import compose.icons.fontawesomeicons.regular.Gem
import compose.icons.fontawesomeicons.solid.ChartLine
import compose.icons.fontawesomeicons.solid.User

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
                    imageVector = FontAwesomeIcons.Regular.Gem,
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

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { selectPage(2) }) {
                Icon(
                    modifier = Modifier.height(48.dp),
                    imageVector = FontAwesomeIcons.Solid.ChartLine,
                    contentDescription = ""
                )
            }

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { selectPage(3) }) {
                Icon(
                    modifier = Modifier.height(48.dp),
                    imageVector = FontAwesomeIcons.Solid.User,
                    contentDescription = ""
                )
            }
        },
    )
}
