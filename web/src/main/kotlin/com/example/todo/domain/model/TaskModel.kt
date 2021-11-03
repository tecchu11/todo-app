package com.example.todo.domain.model

import com.example.todo.enums.TaskStatus
import java.time.ZonedDateTime

data class TaskModel(
    var taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)

data class Task(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)

data class TaskRegistrationModel(
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)

data class TaskUpdateModel(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)
