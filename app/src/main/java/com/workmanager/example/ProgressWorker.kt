package com.workmanager.example

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

class ProgressWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    companion object {
        const val PROGRESS = "Progress"

        private const val DELAY_DURATION = 100L

        private const val TAG = "WorkerManagerTag"

    }

    override suspend fun doWork(): Result {
        for (progress in 0..100) {
            val progressData = workDataOf(PROGRESS to progress)
            setProgress(progressData)

            // do something
            try {
                delay(DELAY_DURATION)
                // Thread.sleep(2000)
            } catch (e: CancellationException) {
                Log.d(TAG, "Cancelled")
            }


            if (isStopped) {
                Log.d(TAG, "isStopped")
                return Result.success(workDataOf(PROGRESS to progress))
            }
        }

        return Result.success(workDataOf(PROGRESS to 100))
    }

}