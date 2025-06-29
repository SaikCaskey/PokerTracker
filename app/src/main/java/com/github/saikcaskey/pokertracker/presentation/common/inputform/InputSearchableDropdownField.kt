package com.github.saikcaskey.pokertracker.presentation.common.inputform

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*

@Composable
fun <T> InputSearchableDropdownField(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemToString: (T) -> String,
    filterItems: Boolean = true,
    onAddNewItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(selectedItem?.let { item -> itemToString(item) } ?: "") }

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
                        Icon(FontAwesomeIcons.Solid.CaretSquareDown, contentDescription = "Dropdown")
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            IconButton(onClick = onAddNewItemClicked) {
                Icon(FontAwesomeIcons.Solid.PlusCircle, contentDescription = "Add new")
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
