package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
fun DashboardProfitSummary(data: DashboardProfitSummaryData) {
    SectionContainer(
        title = "Cashflow",
        content = {
            Text(text = "Now: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(data.nowBalance)
            Text(text = "Year: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(data.yearBalance)
            Text(text = "Month: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(data.monthBalance)

            if (data.upcomingCosts < 0) {
                Text(text = "Upcoming Expenses: ", style = MaterialTheme.typography.bodySmall)
                AnimatedProfitText(data.upcomingCosts)
            }
        },
    )
}
