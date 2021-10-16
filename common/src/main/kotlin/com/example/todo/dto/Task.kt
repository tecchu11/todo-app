package com.example.todo.dto

import com.example.todo.enums.TaskStatus
import java.time.ZonedDateTime

data class TaskDto(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)

data class TaskResponseDto(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)

data class TaskRegistrationDto(
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)

data class TaskUpdateDto(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)
