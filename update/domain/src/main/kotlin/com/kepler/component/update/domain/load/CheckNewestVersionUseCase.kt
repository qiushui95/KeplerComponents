package com.kepler.component.update.domain.load

import com.kepler.component.update.domain.param.CallByUser
import com.kepler.component.update.entity.VersionInfo
import son.ysy.architecture.domain.normal.param1.FlowUseCase1

/**
 * 检查更新信息
 */
interface CheckNewestVersionUseCase : FlowUseCase1<CallByUser, VersionInfo>