package com.github.saikcaskey.pokertracker.ui_compose.common.inputform

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T : Enum<T>> InputDropdownSimple(
    selected: T,
    entries: List<T>,
    label: String? = null,
    onSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("${label?.let { "$it:" } ?: ""} ${selected.name.replace("_", " ")}")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            entries.forEach {
                DropdownMenuItem(
                    text = { Text(it.name.replace("_", " ")) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
