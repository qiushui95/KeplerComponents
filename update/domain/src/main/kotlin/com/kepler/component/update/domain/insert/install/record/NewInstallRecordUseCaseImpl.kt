package com.kepler.component.update.domain.insert.install.record

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param1.BaseFlowUseCase1Impl
import son.ysy.architecture.domain.normal.param2.BaseFlowUseCase2Impl

internal class NewInstallRecordUseCaseImpl(
    koin: Koin
) : BaseFlowUseCase2Impl<VersionId,VersionName, Unit>(), NewInstallRecordUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(param1: VersionId, param2: VersionName): Unit? {
        return updateRepository.newInstallRecord(param1.value,param2.value)
    }
}