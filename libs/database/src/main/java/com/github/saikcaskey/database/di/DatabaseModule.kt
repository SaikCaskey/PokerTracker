package com.github.saikcaskey.database.di

import app.cash.sqldelight.db.SqlDriver
import com.github.saikcaskey.database.dao.EventDaoImpl
import com.github.saikcaskey.database.dao.ExpenseDaoImpl
import com.github.saikcaskey.database.dao.UserDaoImpl
import com.github.saikcaskey.database.dao.VenueDaoImpl
import com.github.saikcaskey.database.driver.DriverFactoryImpl
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.dao.EventDao
import com.github.saikcaskey.pokertracker.domain.dao.ExpenseDao
import com.github.saikcaskey.pokertracker.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.domain.dao.VenueDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { DriverFactoryImpl(get()).createDriver() }
    single<PokerTrackerDatabase> { PokerTrackerDatabase(get()) }
    single<UserDao> { UserDaoImpl(get(), get()) }
    single<ExpenseDao> { ExpenseDaoImpl(get(), get()) }
    single<EventDao> { EventDaoImpl(get(), get()) }
    single<VenueDao> { VenueDaoImpl(get(), get()) }
}

object PokerTrackerDatabaseProvider : KoinComponent {
    fun provide(): PokerTrackerDatabase = get()
}
