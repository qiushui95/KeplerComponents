package com.kepler.component.update.domain.insert.ignore

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param2.BaseFlowUseCase2Impl

internal class NewIgnoreRecordUseCaseImpl(
    koin: Koin
) : BaseFlowUseCase2Impl<VersionId, VersionName, Unit>(), NewIgnoreRecordUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(param1: VersionId, param2: VersionName): Unit? {
        return updateRepository.newIgnoreRecord(param1.value, param2.value)
    }
}