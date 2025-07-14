package com.github.saikcaskey.pokertracker.planner.composables

import android.R.attr.name
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun PlannerDaysOfWeekTitle(daysOfWeek: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (name in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 10.sp)
            )
        }
    }
}
