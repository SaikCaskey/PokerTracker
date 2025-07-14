package com.github.saikcaskey.pokertracker.planner.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.saikcaskey.pokertracker.domain.components.PlannerFeatureComponent
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths

@Composable
fun PlannerFeatureContent(component: PlannerFeatureComponent) {
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
                    PlannerDaysOfWeekTitle(daysOfWeek = daysOfWeek())
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
