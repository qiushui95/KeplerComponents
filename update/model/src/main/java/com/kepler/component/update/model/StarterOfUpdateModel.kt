package com.kepler.component.update.model

import android.app.Application
import com.kepler.component.update.model.di.RoomModule
import org.koin.android.ext.android.getKoin
import son.ysy.lib.starter.BaseStarter

object StarterOfUpdateModel : BaseStarter() {
    override fun execute(application: Application) {
        application.getKoin().loadModules(
            listOf(RoomModule().modules)
        )
    }
}