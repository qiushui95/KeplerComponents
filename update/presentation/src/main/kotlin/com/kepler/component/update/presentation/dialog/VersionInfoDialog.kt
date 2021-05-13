package com.kepler.component.update.presentation.dialog

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.kepler.component.update.entity.ApkDownloadStatus
import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.presentation.R
import com.kepler.component.update.presentation.vm.VersionViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import razerdp.basepopup.BasePopupWindow
import son.ysy.architecture.domain.DomainResult

abstract class VersionInfoDialog : BasePopupWindow, LifecycleOwner {

    private lateinit var viewModel: VersionViewModel


    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }


    private val parentJob by lazy {
        SupervisorJob(lifecycleScope.coroutineContext.job)
    }

    constructor(fragment: Fragment) : super(fragment) {
        bindLifecycleOwner(fragment.viewLifecycleOwner)
        viewModel = fragment.getViewModel()
    }

    constructor(activity: FragmentActivity) : super(activity) {
        viewModel = activity.getViewModel()
    }

    private val downloadDelegate by lazy {
        DownloadDelegate(viewModel, context)
    }


    final override fun bindLifecycleOwner(lifecycleOwner: LifecycleOwner?): BasePopupWindow {
        return super.bindLifecycleOwner(lifecycleOwner)
    }

    @CallSuper
    override fun onViewCreated(contentView: View) {

        isOutSideTouchable = false

        setOutSideDismiss(false)

        setBackPressEnable(false)

        popupGravity = Gravity.CENTER

        super.onViewCreated(contentView)

        getCloseButton().setOnClickListener {
            val versionInfo = downloadDelegate.currentVersionInfo() ?: return@setOnClickListener
            viewModel.ignoreUpdate(versionInfo)
            dismiss()
        }
    }

    protected abstract fun getCloseButton(): View

    protected abstract fun getContinueButton(): TextView

    fun updateVersionInfo(versionInfo: VersionInfo): VersionInfoDialog {
        downloadDelegate.updateVersionInfo(versionInfo)
        if (isShowing) {
            bindVersionInfo()
        }
        return this
    }

    private fun updateUi(status: ApkDownloadStatus) {

        val button = getContinueButton()

        when (status) {
            is ApkDownloadStatus.DownloadFailed -> {
                button.text = context.getString(
                    R.string.string_version_info_dialog_btn_download_failed,
                    status.percent
                )

                setDownloadClick(button)
            }
            is ApkDownloadStatus.DownloadSuccess -> {
                button.setText(R.string.string_version_info_dialog_btn_install)
                button.setOnClickListener {
                    downloadDelegate.startInstall(status.path)
                }
            }
            is ApkDownloadStatus.Downloading -> {
                button.text = context.getString(
                    R.string.string_version_info_dialog_btn_downloading,
                    status.percent
                )

                setDownloadClick(button)
            }
            ApkDownloadStatus.NeedDownload -> {
                button.setText(R.string.string_version_info_dialog_btn_download)
                setDownloadClick(button)
            }
        }
    }

    private fun setDownloadClick(button: View) {
        button.setOnClickListener {
            downloadDelegate.startDownload()
        }
    }


    @CallSuper
    override fun onShowing() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        super.onShowing()

        bindVersionInfo()

        downloadDelegate.register()
    }

    private fun bindVersionInfo() {
        val versionInfo = downloadDelegate.currentVersionInfo() ?: return

        getCloseButton().isGone = versionInfo.isForce

        parentJob.cancelChildren()

        lifecycleScope.launch(parentJob) {
            viewModel.observeDownloadStatus(versionInfo.id)
                .filterIsInstance<DomainResult.Data<ApkDownloadStatus>>()
                .map { it.data }
                .onEach { updateUi(it) }
                .launchIn(this)
        }
    }


    @CallSuper
    override fun onDismiss() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

        parentJob.cancelChildren()
        super.onDismiss()

        downloadDelegate.stopAllTask()

        downloadDelegate.unRegister()
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}