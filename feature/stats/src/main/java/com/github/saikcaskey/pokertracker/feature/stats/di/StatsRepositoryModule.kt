package com.github.saikcaskey.pokertracker.feature.stats.di

import com.github.saikcaskey.pokertracker.feature.stats.data.repository.EventRepositoryImpl
import com.github.saikcaskey.pokertracker.feature.stats.data.repository.ExpenseRepositoryImpl
import com.github.saikcaskey.pokertracker.feature.stats.data.repository.UserRepositoryImpl
import com.github.saikcaskey.pokertracker.feature.stats.data.repository.VenueRepositoryImpl
import com.github.saikcaskey.pokertracker.libs.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.VenueRepository
import org.koin.dsl.module

val statsRepositoryModule = module {
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<VenueRepository> { VenueRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}
