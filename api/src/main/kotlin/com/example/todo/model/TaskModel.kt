package com.example.todo.model

import com.example.todo.enumration.TaskStatus
import java.time.Instant

data class TaskModel(
    val taskId: Int,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val taskStatus: TaskStatus,
    val registeredAt: Instant?,
    val updatedAt: Instant?
)
