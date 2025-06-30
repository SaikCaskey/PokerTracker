package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.data.repository.EventRepositoryImpl
import com.github.saikcaskey.pokertracker.data.repository.ExpenseRepositoryImpl
import com.github.saikcaskey.pokertracker.data.repository.VenueRepositoryImpl
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.shared.domain.repository.ExpenseRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val repositoryModule = module {
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