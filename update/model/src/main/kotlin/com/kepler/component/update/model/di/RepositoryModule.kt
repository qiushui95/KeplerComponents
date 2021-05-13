package com.kepler.component.update.model.di

import com.kepler.component.update.model.repository.UpdateRepository
import com.kepler.component.update.model.repository.UpdateRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

internal class RepositoryModule {

    val modules = module {
        single {
            UpdateRepositoryImpl(get())
        } bind UpdateRepository::class
    }
}