package com.workmanager.example

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : Worker(appContext, workerParameters) {

    companion object {
        const val KEY_INPUT_TITLE = "title"
        const val KEY_OUTPUT_DATA = "output"

        private const val TAG = "WorkerManagerTag"
    }

    //**********************************************************************************************
    override fun doWork(): Result {

        val title = inputData.getString(KEY_INPUT_TITLE) ?: return Result.failure()

        Log.d(TAG, "doWork: start= $title")

        Thread.sleep(5000)

        Log.d(TAG, "doWork: complete = ${Thread.currentThread().name}")

        val outputData = Data.Builder()
            .putString(KEY_OUTPUT_DATA, "output test")
            .build()

        return Result.success(outputData)
    }

}