package com.example.vero_android_task.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskClass::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}