package com.kepler.component.update.model.di

import androidx.room.Room
import com.kepler.component.update.model.room.UpdateDatabase
import org.koin.dsl.module

internal class RoomModule {

    val modules = module {
        single {
            Room.databaseBuilder(get(), UpdateDatabase::class.java, "Update.db")
                .build()
        }

        single {
            get<UpdateDatabase>().getUpdateDao()
        }
    }
}