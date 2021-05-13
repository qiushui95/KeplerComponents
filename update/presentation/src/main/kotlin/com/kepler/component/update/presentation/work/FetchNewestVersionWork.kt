package com.kepler.component.update.presentation.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kepler.component.update.domain.load.CheckNewestVersionUseCase
import com.kepler.component.update.domain.param.CallByUser
import com.kepler.component.update.presentation.StarterOfUpdate
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.getKoin
import org.koin.core.Koin
import son.ysy.architecture.error.ErrorReporter

internal class FetchNewestVersionWork(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val koin: Koin by lazy {
        StarterOfUpdate.app
            .getKoin()
    }

    private val checkNewestVersionUseCase: CheckNewestVersionUseCase by koin.inject()

    private val errorReporter: ErrorReporter by koin.inject()

    override suspend fun doWork(): Result {
        checkNewestVersionUseCase(CallByUser(false))
            .catch {
                errorReporter.report(it)
            }
            .collect()

        return Result.success()
    }
}