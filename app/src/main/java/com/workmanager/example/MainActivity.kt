package com.workmanager.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WorkerManagerTag"
    }

    private lateinit var mWorkerManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mWorkerManager = WorkManager.getInstance(this)

        buttonOneTimeWork.setOnClickListener {
            startOneTimeWork()
        }

    }

    //**********************************************************************************************
    private fun startOneTimeWork() {
        val testWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<TestWorker>()
                .setInputData(workDataOf(
                    TestWorker.KEY_INPUT_TITLE to "Test Title"
                ))
                .build()

        mWorkerManager.enqueue(testWorkRequest)
        mWorkerManager.getWorkInfoByIdLiveData(testWorkRequest.id)
            .observe(this, { workerInfo ->

                if (workerInfo?.state == WorkInfo.State.SUCCEEDED) {
                    Log.d(TAG, "worker output= ${workerInfo.outputData.getString(TestWorker.KEY_OUTPUT_DATA)}")
                }

            })
    }

}