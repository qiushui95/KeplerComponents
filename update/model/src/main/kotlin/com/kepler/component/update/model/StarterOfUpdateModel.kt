package com.kepler.component.update.model

import android.app.Application
import com.kepler.component.update.model.di.ApiModule
import com.kepler.component.update.model.di.RepositoryModule
import com.kepler.component.update.model.di.RoomModule
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.http.StarterOfArchitectureHttp
import son.ysy.architecture.starter.BaseStarter

object StarterOfUpdateModel : BaseStarter() {
    override fun execute(application: Application) {
        StarterOfArchitectureHttp(application)

        application.getKoin().loadModules(
            listOf(
                RoomModule().modules,
                ApiModule().modules,
                RepositoryModule().modules,
            )
        )
    }
}