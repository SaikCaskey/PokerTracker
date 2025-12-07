package com.github.saikcaskey.pokertracker.app.di

import com.github.saikcaskey.pokertracker.app.generators.GeneratorUsernamesImpl
import com.github.saikcaskey.pokertracker.libs.domain.generators.GeneratorUsernames
import org.koin.dsl.module

val generatorsModule = module {
    single<GeneratorUsernames> { GeneratorUsernamesImpl() }
}
