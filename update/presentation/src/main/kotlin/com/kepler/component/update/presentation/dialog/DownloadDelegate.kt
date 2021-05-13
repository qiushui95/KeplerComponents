package com.kepler.component.update.presentation.dialog

import android.content.Context
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTaskListener
import com.arialyy.aria.core.inf.IEntity
import com.arialyy.aria.core.task.DownloadTask
import com.arialyy.aria.util.ALog
import com.blankj.utilcode.util.AppUtils
import com.kepler.component.update.domain.param.VersionId
import com.kepler.component.update.domain.param.VersionName
import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.presentation.vm.VersionViewModel
import java.io.File
import java.util.concurrent.atomic.AtomicReference

internal class DownloadDelegate(
    private val viewModel: VersionViewModel,
    private val context: Context
) : DownloadTaskListener {
    private val versionInfo = AtomicReference<VersionInfo>()

    private val download by lazy {
        Aria.init(context)
        Aria.download(this)
    }

    fun currentVersionInfo(): VersionInfo? = versionInfo.get()

    fun updateVersionInfo(versionInfo: VersionInfo) {
        this.versionInfo.set(versionInfo)
    }

    fun startDownload() {
        val versionInfo = versionInfo.get()

        val task = download.getFirstDownloadEntity(versionInfo.url)

        if (task == null) {
            val cacheDir = context.externalCacheDir ?: context.cacheDir

            val dir = File(cacheDir, "download")

            dir.mkdir()

            val file = File(dir, "${versionInfo.id}.apk")

            download.load(versionInfo.url)
                .setFilePath(file.absolutePath)
                .ignoreFilePathOccupy()
                .ignoreCheckPermissions()
                .create()
        } else if (task.state in intArrayOf(
                IEntity.STATE_CANCEL,
                IEntity.STATE_FAIL,
                IEntity.STATE_STOP,
                IEntity.STATE_WAIT,
                IEntity.STATE_CANCEL,
            )
        ) {
            download.load(task.id)
                .resume()
        }
    }

    fun register() {
        download.register()
    }

    fun unRegister() {
        download.unRegister()
    }

    fun stopAllTask() {
        download.stopAllTask()
    }

    fun startInstall(path: String) {
        val versionInfo = currentVersionInfo() ?: return

        viewModel.startInstall(VersionId(versionInfo.id), VersionName(versionInfo.targetVersion))

        AppUtils.installApp(path)
    }

    override fun onTaskStart(task: DownloadTask?) {
        val updateId = versionInfo.get().id

        viewModel.downloadPercentChanged(VersionId(updateId), 0f)
    }

    override fun onTaskFail(task: DownloadTask?, ex: java.lang.Exception?) {
        val updateId = versionInfo.get().id

        val msg = ex?.message ?: ""

        viewModel.downloadFailed(VersionId(updateId), msg)
    }

    override fun onTaskComplete(task: DownloadTask?) {
        task ?: return

        val updateId = versionInfo.get().id

        viewModel.downloadSuccess(VersionId(updateId), task.filePath)

        startInstall(task.filePath)
    }

    override fun onTaskRunning(task: DownloadTask?) {
        task ?: return

        val percent = task.currentProgress * 100f / task.fileSize

        val updateId = versionInfo.get().id

        viewModel.downloadPercentChanged(VersionId(updateId), percent)
    }

    override fun onWait(task: DownloadTask?) {

    }

    override fun onPre(task: DownloadTask?) {

    }

    override fun onTaskPre(task: DownloadTask?) {

    }

    override fun onTaskResume(task: DownloadTask?) {

    }

    override fun onTaskStop(task: DownloadTask?) {

    }

    override fun onTaskCancel(task: DownloadTask?) {

    }

    override fun onNoSupportBreakPoint(task: DownloadTask?) {

    }
}