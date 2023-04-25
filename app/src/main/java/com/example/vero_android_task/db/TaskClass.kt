package com.example.vero_android_task.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskClass(
    @PrimaryKey val task: String,
    val title: String,
    val description: String,
    val sort: String,
    val wageType: String,
    val businessUnitKey: String? = null,
    val businessUnit: String = "",
    val parentTaskID: String = "",
    val preplanningBoardQuickSelect: String? = null,
    val colorCode: String,
    val workingTime: String? = null,
    val isAvailableInTimeTrackingKioskMode: Boolean
) {
    operator fun iterator(): List<Pair<String, Any?>> {
        return listOf(
            "task" to task,
            "title" to title,
            "description" to description,
            "sort" to sort,
            "wageType" to wageType,
            "businessUnitKey" to businessUnitKey,
            "businessUnit" to businessUnit,
            "parentTaskID" to parentTaskID,
            "preplanningBoardQuickSelect" to preplanningBoardQuickSelect,
            "colorCode" to colorCode,
            "workingTime" to workingTime,
            "isAvailableInTimeTrackingKioskMode" to isAvailableInTimeTrackingKioskMode
        )
    }
}