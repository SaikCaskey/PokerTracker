package com.github.saikcaskey.stats.di

import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.stats.data.repository.EventRepositoryImpl
import com.github.saikcaskey.stats.data.repository.ExpenseRepositoryImpl
import com.github.saikcaskey.stats.data.repository.VenueRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val statsRepositoryModule = module {
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<VenueRepository> { VenueRepositoryImpl(get(), get()) }
}

object EventRepositoryProvider : KoinComponent {
    fun provide(): EventRepository = get()
}

object ExpenseRepositoryProvider : KoinComponent {
    fun provide(): ExpenseRepository = get()
}

object VenueRepositoryProvider : KoinComponent {
    fun provide(): VenueRepository = get()
}
