package com.example.todo.domain.entity

import com.example.todo.domain.enumration.TaskStatus
import java.time.OffsetDateTime

data class TaskEntity(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class Task(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)
