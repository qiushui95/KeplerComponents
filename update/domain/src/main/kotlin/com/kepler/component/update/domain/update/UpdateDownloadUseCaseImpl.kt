package com.kepler.component.update.domain.update

import com.kepler.component.update.model.repository.UpdateRepository
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param1.BaseFlowUseCase1Impl

internal class UpdateDownloadUseCaseImpl(
    koin: Koin
) : BaseFlowUseCase1Impl<UpdateDownloadParam, Unit>(), UpdateDownloadUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override suspend fun execute(param: UpdateDownloadParam): Unit? {
        return when (param) {
            is UpdateDownloadParam.Downloading -> {
                updateRepository.updateDownloadPercent(param.id.value, param.percent)
            }
            is UpdateDownloadParam.Failed -> {
                updateRepository.updateDownloadFailed(param.id.value, param.msg)
            }
            is UpdateDownloadParam.Success -> {
                updateRepository.updateDownloadSuccess(param.id.value, param.path)
            }
        }
    }
}