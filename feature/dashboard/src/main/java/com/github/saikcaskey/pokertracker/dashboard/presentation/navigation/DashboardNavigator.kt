package com.github.saikcaskey.pokertracker.dashboard.presentation.navigation

import com.github.saikcaskey.pokertracker.domain.presentation.RootNavigator

interface DashboardNavigator {
    fun onShowEventDetail(id: Long)
    fun onShowExpenseDetail(id: Long)
    fun onShowVenueDetail(id: Long)
    fun onShowInsertEvent()
    fun onShowInsertVenue()
    fun onShowInsertExpense()
    fun onShowAllEvents()
    fun onShowAllExpenses()
    fun onShowAllVenues()

    companion object {
        fun from(navigator: RootNavigator): DashboardNavigator {
            return object : DashboardNavigator {
                override fun onShowEventDetail(id: Long) {
                    navigator.onShowEventDetail(id)
                }

                override fun onShowExpenseDetail(id: Long) {
                    navigator.onShowExpenseDetail(id)
                }

                override fun onShowVenueDetail(id: Long) {
                    navigator.onShowVenueDetail(id)
                }

                override fun onShowInsertEvent() {
                    navigator.onShowInsertEvent()
                }

                override fun onShowInsertVenue() {
                    navigator.onShowInsertVenue()
                }

                override fun onShowInsertExpense() {
                    navigator.onShowInsertExpense()
                }

                override fun onShowAllEvents() {
                    navigator.onShowAllEvents()
                }

                override fun onShowAllExpenses() {
                    navigator.onShowAllExpenses()
                }

                override fun onShowAllVenues() {
                    navigator.onShowAllVenues()
                }
            }
        }
    }
}

