package com.example.vero_android_task.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vero_android_task.db.TaskDatabase
import com.example.vero_android_task.repository.ApiCall
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val apiCall = ApiCall()
        val allTasksLists = apiCall.getData()
        if (allTasksLists.isNotEmpty()) {
            val db = Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java, "tasks"
            ).build()
            db.clearAllTables()
            for (i in allTasksLists.indices) {
                db.taskDao().insert(allTasksLists[i])
            }
            return Result.success()
        }
        return Result.failure()
    }

    companion object {
        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val worker = PeriodicWorkRequestBuilder<MyWorker>(60, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork("my_worker", ExistingPeriodicWorkPolicy.KEEP, worker)
        }
    }
}