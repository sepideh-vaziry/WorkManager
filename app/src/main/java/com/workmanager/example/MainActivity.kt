package com.workmanager.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workerManager = WorkManager.getInstance(this)

        val testWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<TestWorker>()
                .setInputData(workDataOf(
                    TestWorker.KEY_INPUT_TITLE to "Test Title"
                ))
                .build()

        workerManager.enqueue(testWorkRequest)
        workerManager.getWorkInfoByIdLiveData(testWorkRequest.id)
            .observe(this, { workerInfo ->

                if (workerInfo?.state == WorkInfo.State.SUCCEEDED) {
                    Log.d("sepideh", "worker output= ${workerInfo.outputData.getString(TestWorker.KEY_OUTPUT_DATA)}")
                }

            })

    }

}