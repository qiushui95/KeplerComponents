package com.kepler.component.update.domain.di

import com.kepler.component.update.domain.insert.ignore.NewIgnoreRecordUseCase
import com.kepler.component.update.domain.insert.ignore.NewIgnoreRecordUseCaseImpl
import com.kepler.component.update.domain.insert.install.log.NewInstallLogUseCase
import com.kepler.component.update.domain.insert.install.log.NewInstallLogUseCaseImpl
import com.kepler.component.update.domain.insert.install.record.NewInstallRecordUseCase
import com.kepler.component.update.domain.insert.install.record.NewInstallRecordUseCaseImpl
import com.kepler.component.update.domain.load.CheckNewestVersionUseCase
import com.kepler.component.update.domain.load.CheckNewestVersionUseCaseImpl
import com.kepler.component.update.domain.observe.ObserveDownloadStatusUseCase
import com.kepler.component.update.domain.observe.ObserveDownloadStatusUseCaseImpl
import com.kepler.component.update.domain.observe.ObserveVersionInfoUseCase
import com.kepler.component.update.domain.observe.ObserveVersionInfoUseCaseImpl
import com.kepler.component.update.domain.update.UpdateDownloadUseCase
import com.kepler.component.update.domain.update.UpdateDownloadUseCaseImpl
import com.kepler.component.update.domain.upload.UploadUpdateLogUseCase
import com.kepler.component.update.domain.upload.UploadUpdateLogUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

internal class UseCaseModule {
    val modules = module {
        single {
            NewIgnoreRecordUseCaseImpl(get())
        } bind NewIgnoreRecordUseCase::class

        single {
            NewInstallLogUseCaseImpl(get())
        } bind NewInstallLogUseCase::class

        single {
            NewInstallRecordUseCaseImpl(get())
        } bind NewInstallRecordUseCase::class

        single {
            ObserveVersionInfoUseCaseImpl(get())
        } bind ObserveVersionInfoUseCase::class

        single {
            UpdateDownloadUseCaseImpl(get())
        } bind UpdateDownloadUseCase::class

        single {
            UploadUpdateLogUseCaseImpl(get())
        } bind UploadUpdateLogUseCase::class

        single {
            ObserveDownloadStatusUseCaseImpl(get())
        } bind ObserveDownloadStatusUseCase::class

        single {
            CheckNewestVersionUseCaseImpl(get())
        } bind CheckNewestVersionUseCase::class
    }
}