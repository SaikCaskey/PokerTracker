package com.github.saikcaskey.pokertracker.shared.presentation.expense

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.adjustedAmount
import com.github.saikcaskey.pokertracker.shared.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.components.ViewExpensesComponent.*
import com.github.saikcaskey.pokertracker.domain.components.ViewExpensesComponent.ExpenseSortOption.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlin.collections.filter

class DefaultViewExpensesComponent(
    componentContext: ComponentContext,
    private val expenseRepository: ExpenseRepository,
    private val onShowInsertExpense: () -> Unit,
    private val onShowExpenseDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    dispatchers: CoroutineDispatchers,
) : ViewExpensesComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _expenseSortOption = MutableStateFlow(CREATED_AT_DESC
    )
    private val _searchOptions = combine(_searchQuery, _expenseSortOption, ::ExpenseSearchFilter)
        .stateIn(coroutineScope, Eagerly, ExpenseSearchFilter())

    override val uiState: StateFlow<UiState> =
        combine(expenseRepository.getAll(), _searchOptions) { expenses, searchFilter ->
            val filtered = expenses
                .filter { expense ->
                    searchFilter.query.isNullOrBlank()
                            || expense.type.name.contains(searchFilter.query, ignoreCase = true)
                            || expense.description?.contains(searchFilter.query, ignoreCase = true) == true
                            || expense.id == searchFilter.query.toLongOrNull()
                            || expense.venueId == searchFilter.query.toLongOrNull()
                            || expense.eventId == searchFilter.query.toLongOrNull()
                            || expense.amount.toString().contains(searchFilter.query, ignoreCase = true)
                }
                .sortedWith(
                    when (searchFilter.sort) {
                        AMOUNT_ASC -> compareBy(Expense::adjustedAmount)
                        AMOUNT_DESC -> compareByDescending(Expense::adjustedAmount)
                        ID_ASC -> compareBy(Expense::id)
                        ID_DESC -> compareByDescending(Expense::id)
                        CREATED_AT_ASC -> compareBy(Expense::createdAt)
                        CREATED_AT_DESC -> compareByDescending(Expense::createdAt)
                        UPDATED_AT_ASC -> compareBy(Expense::updatedAt)
                        UPDATED_AT_DESC -> compareByDescending(Expense::updatedAt)
                    }
                )
            UiState(expenses, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: ExpenseSortOption) {
        _expenseSortOption.value = sortOption
    }

    override fun onShowExpenseDetailClicked(expenseId: Long) = onShowExpenseDetail(expenseId)

    override fun onShowInsertExpenseClicked() = onShowInsertExpense()

    override fun onDeleteExpenseClicked(id: Long) {
        coroutineScope.launch {
            runCatching { expenseRepository.deleteById(id) }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onDeleteAllExpensesClicked() {
        coroutineScope.launch {
            runCatching { expenseRepository.deleteAll() }
                .onFailure(Throwable::printStackTrace)
        }
    }
}