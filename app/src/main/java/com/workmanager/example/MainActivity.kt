package com.workmanager.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WorkerManagerTag"
    }

    private lateinit var mWorkerManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWorkerManager = WorkManager.getInstance(applicationContext)

        buttonOneTimeWork.setOnClickListener {
            startOneTimeWork()
        }

        buttonProgressWorker.setOnClickListener {
            startProgressWorker()
        }

        buttonPeriodicWork.setOnClickListener {
            startPeriodicWork()
        }

    }

    //**********************************************************************************************
    private fun startOneTimeWork() {
        val testWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TestWorker>()
                .setInputData(workDataOf(
                    TestWorker.KEY_INPUT_TITLE to "Test Title"
                ))
                .build()

        mWorkerManager.enqueue(testWorkRequest)
        mWorkerManager.getWorkInfoByIdLiveData(testWorkRequest.id)
            .observe(this, { workInfo: WorkInfo? ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    Log.d(TAG, "worker output= ${workInfo.outputData.getString(TestWorker.KEY_OUTPUT_DATA)}")
                }
            })
    }

    //**********************************************************************************************
    private fun startProgressWorker() {
        val progressWorker: WorkRequest = OneTimeWorkRequestBuilder<ProgressWorker>()
                .build()

        mWorkerManager.enqueue(progressWorker)

        mWorkerManager.getWorkInfoByIdLiveData(progressWorker.id)
            .observe(this, { workInfo: WorkInfo? ->

                workInfo?.let {

                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        val progress = workInfo.outputData.getInt(ProgressWorker.PROGRESS, 0)
                        Log.d(TAG, "status= ${workInfo.state} - progress is: $progress  ")
                    }
                    else {
                        val progress = it.progress
                        val progressValue = progress.getInt(ProgressWorker.PROGRESS, -1)

                        Log.d(TAG, "status= ${workInfo.state} - progress is: $progressValue")
                    }

                }

            })
    }

    //**********************************************************************************************
    private fun startPeriodicWork() {
        val testWorkRequest: WorkRequest = PeriodicWorkRequestBuilder<TestWorker>(
            15, TimeUnit.MINUTES
        ).setInputData(workDataOf(
            TestWorker.KEY_INPUT_TITLE to "Test Title"
        )).build()

        mWorkerManager.enqueue(testWorkRequest)
        mWorkerManager.getWorkInfoByIdLiveData(testWorkRequest.id)
            .observe(this, { workInfo: WorkInfo? ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    Log.d(TAG, "worker output= ${workInfo.outputData.getString(TestWorker.KEY_OUTPUT_DATA)}")
                }
            })
    }

}