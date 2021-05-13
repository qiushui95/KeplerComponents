package com.kepler.component.update.domain.upload

import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param0.BaseFlowUseCaseImpl

internal class UploadUpdateLogUseCaseImpl(
    koin: Koin
) : BaseFlowUseCaseImpl<Unit>(), UploadUpdateLogUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(): Unit? {
        return updateRepository.uploadUpdateLog()
    }
}