package com.github.saikcaskey.pokertracker.presentation.common.inputform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.*

@Composable
fun DatePicker(
    value: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    val dates = remember(startDate, endDate) {
        startDate.generateDateRangeUntil(endDate)
    }
    val listState = rememberLazyListState()

    LaunchedEffect(value) {
        val selectedIndex = dates.indexOf(value)
        if (selectedIndex >= 10) {
            listState.scrollToItem(selectedIndex - 2)
        } else if (selectedIndex >= 0) {
            listState.scrollToItem(selectedIndex)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.height(200.dp),
    ) {
        items(dates) { date ->
            val isSelected = date == value
            TextButton(
                onClick = { onDateSelected(date) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(date.toString())
            }
        }
    }
}

// Helper to generate range
private fun LocalDate.generateDateRangeUntil(to: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var current = this
    while (current <= to) {
        dates.add(current)
        current = current.plus(DatePeriod(days = 1))
    }
    return dates
}

@Composable
fun TimePicker(
    selectedHour: Int,
    selectedMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Hour picker
        PickerColumn(0..23, selectedHour) { onTimeSelected(it, selectedMinute) }

        Spacer(Modifier.width(16.dp))

        // Minute picker
        PickerColumn(0..59, selectedMinute) { onTimeSelected(selectedHour, it) }
    }
}

@Composable
fun PickerColumn(
    range: IntRange,
    selectedValue: Int,
    onValueSelected: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedValue) {
        val selectedIndex = range.toList().indexOf(selectedValue)
        if (selectedIndex >= 4) {
            listState.scrollToItem(selectedIndex - 1)
        } else if (selectedIndex >= 0) {
            listState.scrollToItem(selectedIndex)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(150.dp)
            .width(60.dp)
    ) {
        items(range.toList()) { value ->
            val isSelected = value == selectedValue
            TextButton(
                onClick = { onValueSelected(value) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("$value")
            }
        }
    }
}

@Composable
fun InputDateField(
    label: String = "Select Date",
    value: LocalDate? = null,
    modifier: Modifier = Modifier,
    onValueChange: (LocalDate) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(value) }

    val displayText = selectedDateTime?.toString().orEmpty()

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showDialog = true
            }
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {}, // read-only
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    showDialog = true
                }
            },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
        )
    }

    if (showDialog) {
        DatePickerDialog(
            initialDate = selectedDateTime,
            onDismissRequest = { showDialog = false },
            onDateSelected = { date ->
                selectedDateTime = date
                onValueChange(date)
                showDialog = false
            }
        )
    }
}

@Composable
fun InputTimeField(
    label: String = "Select Time",
    value: LocalTime? = null,
    modifier: Modifier = Modifier,
    onValueChange: (LocalTime?) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(value) }

    // Format LocalDateTime for display (simple ISO format here, customize as needed)
    val displayText = selectedDateTime?.toString() ?: "Tap to select"

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showDialog = true
            }
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {}, // read-only
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    showDialog = true
                }
            },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
        )
    }

    if (showDialog) {
        TimePickerDialog(
            initialDateTime = selectedDateTime ?: Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onDismissRequest = { showDialog = false },
            onTimeSelected = { time ->
                selectedDateTime = time
                onValueChange(time)
                showDialog = false
            }
        )
    }
}

@Composable
fun TimePickerDialog(
    initialDateTime: LocalTime,
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
) {
    var selectedHour by remember { mutableStateOf(initialDateTime.hour) }
    var selectedMinute by remember { mutableStateOf(initialDateTime.minute) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Time picker part
                TimePicker(selectedHour, selectedMinute) { hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                }

                Spacer(Modifier.height(24.dp))

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        onTimeSelected(
                            LocalTime(
                                selectedHour,
                                selectedMinute,
                                second = 0
                            )
                        )
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    initialDate: LocalDate? = null,
    startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        .minus(DatePeriod(years = 2)),
    endDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        .plus(DatePeriod(years = 2)),
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    // State for selections
    var selectedDate by remember {
        mutableStateOf(
            initialDate ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Date picker part
                DatePicker(
                    selectedDate,
                    startDate = startDate,
                    endDate = endDate
                ) { value -> selectedDate = value }

                Spacer(Modifier.height(16.dp))

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        onDateSelected(
                            LocalDate(
                                selectedDate.year,
                                selectedDate.monthNumber,
                                selectedDate.dayOfMonth,
                            )
                        )
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}
