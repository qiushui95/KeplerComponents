package com.kepler.component.update.domain.observe

import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.entity.ApkDownloadStatus
import son.ysy.architecture.domain.normal.param1.FlowUseCase1

/**
 * 订阅版本apk下载状态
 */
interface ObserveDownloadStatusUseCase : FlowUseCase1<VersionId, ApkDownloadStatus>