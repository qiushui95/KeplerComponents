package com.kepler.component.update.presentation

import android.app.Activity
import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.work.*
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.kepler.component.update.domain.StarterOfUpdateDomain
import com.kepler.component.update.presentation.vm.VersionViewModel
import com.kepler.component.update.presentation.work.FetchNewestVersionWork
import com.kepler.component.update.presentation.work.UploadUpdateLogWork
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module
import son.ysy.architecture.starter.BaseStarter
import java.util.concurrent.TimeUnit

object StarterOfUpdate : BaseStarter() {
    override fun execute(application: Application) {
        StarterOfUpdateDomain(application)
        application.getKoin().loadModules(listOf(
            module {
                viewModel {
                    VersionViewModel(get())
                }
            }
        ))

        ActivityUtils.addActivityLifecycleCallbacks(object : Utils.ActivityLifecycleCallbacks() {
            override fun onActivityResumed(activity: Activity) {

                if (activity is FragmentActivity) {
                    activity.getViewModel<VersionViewModel>().createInstallLog()
                }
            }
        })

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val fetchNewestVersionWork = PeriodicWorkRequestBuilder<FetchNewestVersionWork>(
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        val uploadUpdateLogWork = PeriodicWorkRequestBuilder<UploadUpdateLogWork>(
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(application)

        workManager.enqueueUniquePeriodicWork(
            "FetchNewestVersionWork",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchNewestVersionWork
        )

        workManager.enqueueUniquePeriodicWork(
            "UploadUpdateLogWork",
            ExistingPeriodicWorkPolicy.KEEP,
            uploadUpdateLogWork
        )
    }
}