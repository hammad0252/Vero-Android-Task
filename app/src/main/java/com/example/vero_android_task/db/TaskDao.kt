package com.example.vero_android_task.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<TaskClass>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: TaskClass)
}