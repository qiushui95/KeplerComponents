package com.kepler.component.update.domain.insert.install.log

import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param0.BaseFlowUseCaseImpl

internal class NewInstallLogUseCaseImpl(
    koin: Koin
) : BaseFlowUseCaseImpl<Unit>(), NewInstallLogUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(): Unit? {
        return updateRepository.newInstallLog()
    }
}