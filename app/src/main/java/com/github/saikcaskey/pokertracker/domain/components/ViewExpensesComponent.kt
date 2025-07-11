package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Expense
import kotlinx.coroutines.flow.StateFlow

interface ViewExpensesComponent {

    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onSearchQueryChanged(query: String?)
    fun onShowInsertExpenseClicked()
    fun onShowExpenseDetailClicked(expenseId: Long)
    fun onDeleteExpenseClicked(id: Long)
    fun onDeleteAllExpensesClicked()

    data class UiState(
        val events: List<Expense> = emptyList(),
        val filtered: List<Expense> = emptyList(),
        val searchFilter: ExpenseSearchFilter = ExpenseSearchFilter(),
    )

    enum class ExpenseSortOption {
        AMOUNT_ASC, AMOUNT_DESC,
        ID_ASC, ID_DESC,
        CREATED_AT_ASC, CREATED_AT_DESC,
        UPDATED_AT_ASC, UPDATED_AT_DESC,
    }

    data class ExpenseSearchFilter(
        val query: String? = null,
        val sort: ExpenseSortOption = ExpenseSortOption.AMOUNT_DESC,
    )

    fun onFilterOptionChanged(sortOption: ExpenseSortOption)
}
