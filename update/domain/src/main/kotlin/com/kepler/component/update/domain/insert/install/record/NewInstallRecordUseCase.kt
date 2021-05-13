package com.kepler.component.update.domain.insert.install.record

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import son.ysy.architecture.domain.normal.param1.FlowUseCase1
import son.ysy.architecture.domain.normal.param2.FlowUseCase2

/**
 * 新增更新安装记录
 */
interface NewInstallRecordUseCase : FlowUseCase2<VersionId,VersionName, Unit>