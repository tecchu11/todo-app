package com.example.todo.application.form

import com.example.todo.domain.enumration.TaskStatus
import java.time.Instant

data class TaskForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val taskStatus: TaskStatus,
    val registeredAt: Instant?,
    val updatedAt: Instant?
)