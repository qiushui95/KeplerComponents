package com.kepler.component.update.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.kepler.component.update.domain.insert.ignore.NewIgnoreRecordUseCase
import com.kepler.component.update.domain.insert.install.log.NewInstallLogUseCase
import com.kepler.component.update.domain.insert.install.record.NewInstallRecordUseCase
import com.kepler.component.update.domain.load.CheckNewestVersionUseCase
import com.kepler.component.update.domain.observe.ObserveDownloadStatusUseCase
import com.kepler.component.update.domain.observe.ObserveVersionInfoUseCase
import com.kepler.component.update.domain.param.CallByUser
import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import com.kepler.component.update.domain.update.UpdateDownloadParam
import com.kepler.component.update.domain.update.UpdateDownloadUseCase
import com.kepler.component.update.entity.VersionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.Koin
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.error.ErrorReporter

class VersionViewModel(koin: Koin) : ViewModel() {

    private val checkNewestVersionUseCase: CheckNewestVersionUseCase by koin.inject()

    private val observeVersionInfoUseCase: ObserveVersionInfoUseCase by koin.inject()

    private val observeDownloadStatusUseCase: ObserveDownloadStatusUseCase by koin.inject()

    private val newIgnoreRecordUseCase: NewIgnoreRecordUseCase by koin.inject()

    private val newInstallRecordUseCase: NewInstallRecordUseCase by koin.inject()

    private val newInstallLogUseCase: NewInstallLogUseCase by koin.inject()

    private val updateDownloadUseCase: UpdateDownloadUseCase by koin.inject()

    private val errorReporter: ErrorReporter by koin.inject()

    suspend fun checkUpdate(callByUser: Boolean): VersionInfo? {
        return checkNewestVersionUseCase(CallByUser(callByUser))
            .map {
                (it as? DomainResult.Data)?.data
            }.catch {
                errorReporter.report(it)
            }.flowOn(Dispatchers.IO)
            .lastOrNull()
    }

    fun observeVersionInfo() = observeVersionInfoUseCase()
        .map {
            (it as? DomainResult.Data<VersionInfo>)?.data
        }

    fun observeDownloadStatus(id: String) = observeDownloadStatusUseCase(VersionId(id))

    fun ignoreUpdate(versionInfo: VersionInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            newIgnoreRecordUseCase(
                VersionId(versionInfo.id),
                VersionName(versionInfo.targetVersion)
            )
                .collect()
        }
    }

    fun startInstall(id: VersionId,versionName: VersionName) {
        viewModelScope.launch(Dispatchers.IO) {
            newInstallRecordUseCase(id,versionName).collect()
        }
    }

    fun createInstallLog() {
        viewModelScope.launch(Dispatchers.IO) {
            newInstallLogUseCase().collect()
        }
    }

    fun downloadFailed(id: VersionId, msg: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateDownloadUseCase(UpdateDownloadParam.Failed(id, msg)).collect()
        }
    }

    fun downloadPercentChanged(id: VersionId, percent: Float) {

        viewModelScope.launch(Dispatchers.IO) {
            updateDownloadUseCase(UpdateDownloadParam.Downloading(id, percent)).collect()
        }
    }

    fun downloadSuccess(id: VersionId, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateDownloadUseCase(UpdateDownloadParam.Success(id, path)).collect()
        }
    }
}