package com.example.myapplication

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyBackgroundWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val message = inputData.getString("notificationMessage")

        showNotification(message ?: "Notificare")

        return Result.success()
    }

    private fun showNotification(message: String) {

    }
}
