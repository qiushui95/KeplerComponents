package com.kepler.component.update.domain

import android.app.Application
import com.kepler.component.update.domain.di.UseCaseModule
import com.kepler.component.update.model.StarterOfUpdateModel
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.starter.BaseStarter

object StarterOfUpdateDomain : BaseStarter() {
    override fun execute(application: Application) {
        StarterOfUpdateModel(application)
        application.getKoin().loadModules(
            listOf(
                UseCaseModule().modules
            )
        )
    }
}