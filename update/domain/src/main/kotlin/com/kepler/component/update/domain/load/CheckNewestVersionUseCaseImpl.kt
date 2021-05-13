package com.kepler.component.update.domain.load

import com.kepler.component.update.domain.param.CallByUser
import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param1.BaseFlowUseCase1Impl

internal class CheckNewestVersionUseCaseImpl(
    koin: Koin
) : BaseFlowUseCase1Impl<CallByUser, VersionInfo>(), CheckNewestVersionUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(param: CallByUser): VersionInfo? {
        return updateRepository.checkNewestVersion(param.value)
    }
}