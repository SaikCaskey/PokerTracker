package com.github.saikcaskey.pokertracker.libs.ui_compose.common.inputform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CaretSquareDown
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
fun <T> InputSearchableDropdownField(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemToString: (T) -> String,
    onAddNewItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
    filterItems: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember {
        mutableStateOf(selectedItem?.let { item -> itemToString(item) } ?: "")
    }

    val filteredItems = if (filterItems) remember(searchText, items) {
        if (searchText.isBlank()) items
        else items.filter { itemToString(it).contains(searchText, ignoreCase = true) }
    } else items

    LaunchedEffect(selectedItem) {
        searchText = selectedItem?.let { itemToString(it) } ?: ""
    }

    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = true
                },
                label = { Text(label) },
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        FontAwesomeIcons.Solid.CaretSquareDown.AsIcon(24.dp, "Select an option",)
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            IconButton(onClick = onAddNewItemClicked) {
                FontAwesomeIcons.Solid.PlusCircle.AsIcon(24.dp, "Add new",)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemToString(item)) },
                    onClick = {
                        searchText = itemToString(item)
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
