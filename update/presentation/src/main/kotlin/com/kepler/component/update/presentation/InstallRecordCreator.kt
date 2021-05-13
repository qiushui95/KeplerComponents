package com.kepler.component.update.presentation

import androidx.fragment.app.FragmentActivity
import com.kepler.component.update.presentation.vm.VersionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class InstallRecordCreator(activity: FragmentActivity) {

    private val viewModel: VersionViewModel by activity.viewModel()

    fun createInstallLog() {
        viewModel.createInstallLog()
    }
}