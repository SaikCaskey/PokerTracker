package com.github.saikcaskey.pokertracker.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.kizitonwose.calendar.compose.*
import com.kizitonwose.calendar.core.*
import com.github.saikcaskey.pokertracker.shared.presentation.main.MainPagerPagePlannerComponent
import kotlinx.datetime.DayOfWeek

@Composable
fun MainPagerPlannerContent(component: MainPagerPagePlannerComponent) {
    val uiState = component.uiState.collectAsState()
    val datesWithEvents = uiState.value.datesWithEvents
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) }
        val endMonth = remember { currentMonth.plusMonths(100) }
        val firstDayOfWeek = remember(::firstDayOfWeekFromLocale)

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = firstDayOfWeek
        )

        VerticalCalendar(
            state = state,
            monthContainer = { month, calendar ->
                Column(modifier = Modifier.padding(12.dp).weight(1f)) {
                    Text(
                        "${month.yearMonth.month.name} - ${month.yearMonth.year}",
                        style = MaterialTheme.typography.displaySmall.copy(fontSize = 10.sp)
                    )
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek())
                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color.LightGray),
                        content = { calendar() }
                    )
                }
            },
            dayContent = { day ->
                val hasEvent = datesWithEvents.contains(day.date)
                Column(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { component.onShowDayDetail(day.date, hasEvent) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "${day.date.dayOfMonth}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = if (hasEvent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    )
                    if (hasEvent) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .padding(top = 2.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.name.firstOrNull()?.toString().orEmpty(),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 10.sp)
            )
        }
    }
}
