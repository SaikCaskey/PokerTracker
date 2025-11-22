package com.github.saikcaskey.pokertracker.stats.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewExpensesComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class ViewExpensesComponentImpl(
    componentContext: ComponentContext,
    private val expenseRepository: ExpenseRepository,
    private val onShowInsertExpense: () -> Unit,
    private val onShowExpenseDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    dispatchers: CoroutineDispatchers,
) : ViewExpensesComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _expenseSortOption = MutableStateFlow(ViewExpensesComponent.ExpenseSortOption.CREATED_AT_DESC)
    private val _searchOptions = combine(_searchQuery, _expenseSortOption,
        ViewExpensesComponent::ExpenseSearchFilter
    )
        .stateIn(coroutineScope, Eagerly, ViewExpensesComponent.ExpenseSearchFilter())

    override val uiState: StateFlow<ViewExpensesComponent.UiState> =
        combine(expenseRepository.getAll(), _searchOptions) { expenses, searchFilter ->
            val filtered = expenses
                .filter { expense ->
                    val query = searchFilter.query.orEmpty()

                    query.isBlank()
                            || expense.type.name.contains(query, ignoreCase = true)
                            || expense.description?.contains(
                        query,
                        ignoreCase = true
                    ) == true
                            || expense.id == query.toLongOrNull()
                            || expense.venueId == query.toLongOrNull()
                            || expense.eventId == query.toLongOrNull()
                            || expense.amount.toString()
                        .contains(query, ignoreCase = true)
                }
                .sortedWith(
                    when (searchFilter.sort) {
                        ViewExpensesComponent.ExpenseSortOption.AMOUNT_ASC -> compareBy(Expense::amount)
                        ViewExpensesComponent.ExpenseSortOption.AMOUNT_DESC -> compareByDescending(Expense::amount)
                        ViewExpensesComponent.ExpenseSortOption.ID_ASC -> compareBy(Expense::id)
                        ViewExpensesComponent.ExpenseSortOption.ID_DESC -> compareByDescending(Expense::id)
                        ViewExpensesComponent.ExpenseSortOption.CREATED_AT_ASC -> compareBy(Expense::createdAt)
                        ViewExpensesComponent.ExpenseSortOption.CREATED_AT_DESC -> compareByDescending(Expense::createdAt)
                        ViewExpensesComponent.ExpenseSortOption.UPDATED_AT_ASC -> compareBy(Expense::updatedAt)
                        ViewExpensesComponent.ExpenseSortOption.UPDATED_AT_DESC -> compareByDescending(Expense::updatedAt)
                    }
                )
            ViewExpensesComponent.UiState(expenses, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, ViewExpensesComponent.UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: ViewExpensesComponent.ExpenseSortOption) {
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
