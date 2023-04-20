package com.example.vero_android_task

data class TaskClass (
    val task: String,
    val title: String,
    val description: String,
    val sort: String,
    val wageType: String,
    val businessUnitKey: String? = null,
    val businessUnit: String = "",
    val parentTaskID: String = "",
    val preplanningBoardQuickSelect: Any? = null,
    val colorCode: String,
    val workingTime: Any? = null,
    val isAvailableInTimeTrackingKioskMode: Boolean
)

/*enum class BusinessUnit(val value: String) {
    Empty(""),
    Gerüstbau("Gerüstbau");

    companion object {
        public fun fromValue(value: String): BusinessUnit = when (value) {
            ""          -> Empty
            "Gerüstbau" -> Gerüstbau
            else        -> throw IllegalArgumentException()
        }
    }
}

enum class ParentTaskID(val value: String) {
    Empty(""),
    The10Aufbau("10 Aufbau");

    companion object {
        public fun fromValue(value: String): ParentTaskID = when (value) {
            ""          -> Empty
            "10 Aufbau" -> The10Aufbau
            else        -> throw IllegalArgumentException()
        }
    }
}*/