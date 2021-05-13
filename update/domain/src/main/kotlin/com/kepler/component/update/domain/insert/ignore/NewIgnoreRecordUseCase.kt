package com.kepler.component.update.domain.insert.ignore

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import son.ysy.architecture.domain.normal.param2.FlowUseCase2

/**
 * 新增更新忽略记录
 */
interface NewIgnoreRecordUseCase : FlowUseCase2<VersionId, VersionName, Unit>