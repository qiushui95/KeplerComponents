package com.kepler.component.update.domain.observe

import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.model.repository.UpdateRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import org.koin.core.Koin
import son.ysy.architecture.domain.normal.param0.BaseFlowUseCaseImpl

internal class ObserveVersionInfoUseCaseImpl(
    koin: Koin
) : BaseFlowUseCaseImpl<VersionInfo>(), ObserveVersionInfoUseCase {

    private val updateRepository: UpdateRepository by koin.inject()

    override fun executeFlow(): Flow<VersionInfo?> {

        val start = DateTime.now().withTimeAtStartOfDay()

        val end = start.plusDays(1)

        return updateRepository.getAvailableVersionInfoFlow(start.millis, end.millis)
    }

    override suspend fun execute(): VersionInfo? {
        return null
    }
}