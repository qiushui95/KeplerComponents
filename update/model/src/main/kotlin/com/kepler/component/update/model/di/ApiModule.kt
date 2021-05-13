package com.kepler.component.update.model.di

import com.kepler.component.update.model.api.UpdateApi
import org.koin.dsl.module
import retrofit2.Retrofit

internal class ApiModule {

    val modules = module {
        single {
            get<Retrofit>().create(UpdateApi::class.java)
        }
    }
}