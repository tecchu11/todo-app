package com.example.todo.dto

import com.example.todo.enums.TaskStatus
import java.time.OffsetDateTime

data class TaskDto(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
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
