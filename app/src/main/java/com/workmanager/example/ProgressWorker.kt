package com.workmanager.example

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ProgressWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    companion object {
        const val PROGRESS = "progress"

        private const val DELAY_DURATION = 1L

    }

    override suspend fun doWork(): Result {

        return Result.success()
    }

}