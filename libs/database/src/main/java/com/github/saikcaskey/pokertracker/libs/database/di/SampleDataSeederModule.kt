package com.github.saikcaskey.pokertracker.libs.database.di

import com.github.saikcaskey.pokertracker.libs.database.seed.SampleDataSeeder
import com.github.saikcaskey.pokertracker.libs.database.seed.SampleDataSeederImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val sampleDataSeederModule = module {
    single<SampleDataSeeder> { SampleDataSeederImpl(get()) }
}

object SampleDataSeederProvider : KoinComponent {
    fun provide(): SampleDataSeeder = get()
}
