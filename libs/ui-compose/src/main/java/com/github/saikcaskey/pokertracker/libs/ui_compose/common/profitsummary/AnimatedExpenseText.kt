package com.github.saikcaskey.pokertracker.libs.ui_compose.common.profitsummary

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import com.github.saikcaskey.pokertracker.ui_compose.extensions.toProfitColor
import com.github.saikcaskey.pokertracker.libs.domain.extensions.formatAsCurrency
import com.github.saikcaskey.pokertracker.libs.domain.models.Expense

@Composable
fun AnimatedExpenseText(
    expense: Expense,
    style: TextStyle = MaterialTheme.typography.displayMedium,
) {
    val animatedBalance by animateFloatAsState(
        targetValue = expense.adjustedAmount.toFloat(),
        animationSpec = tween(durationMillis = 500),
        label = "BalanceAnimation"
    )

    val animatedColor by animateColorAsState(
        targetValue = expense.adjustedAmount.toProfitColor(),
        animationSpec = tween(durationMillis = 500),
        label = "ColorAnimation"
    )

    val displayText = animatedBalance.toDouble().formatAsCurrency()

    Text(
        text = displayText,
        style = style,
        color = animatedColor
    )
}
