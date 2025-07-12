package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
fun DashboardProfitSummary(
    balance: Double,
    balanceForYear: Double,
    balanceForMonth: Double,
    upcomingCosts: Double,
) {
    SectionContainer(
        title = "Cashflow",
        content = {
            if (upcomingCosts < 0) {
                Text(text = "Upcoming Expenses: ", style = MaterialTheme.typography.bodySmall)
                AnimatedProfitText(upcomingCosts)
            }
            Text(text = "This Month: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balanceForMonth)
            Text(text = "This Year: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balanceForYear)
            Text(text = "All Time: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balance)
        },
    )
}
