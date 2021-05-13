package com.kepler.component.update.domain.observe

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.entity.ApkDownloadStatus
import com.kepler.component.update.model.repository.UpdateRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param1.BaseFlowUseCase1Impl

internal class ObserveDownloadStatusUseCaseImpl(
    koin: Koin
) : BaseFlowUseCase1Impl<VersionId, ApkDownloadStatus>(), ObserveDownloadStatusUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override fun executeFlow(param: VersionId): Flow<ApkDownloadStatus?> {
        return updateRepository.getDownloadStatusFlow(param.value)
    }

    override suspend fun execute(param: VersionId): ApkDownloadStatus? {
        return null
    }
}