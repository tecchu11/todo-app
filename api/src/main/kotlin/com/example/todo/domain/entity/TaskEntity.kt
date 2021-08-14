package com.example.todo.domain.entity

import com.example.todo.domain.enumration.TaskStatus
import java.time.Instant

data class TaskEntity(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: Instant,
    val updatedAt: Instant
)

data class Task(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
)


