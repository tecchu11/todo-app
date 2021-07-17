package com.example.todo.form

import com.example.todo.enumration.TaskStatus
import java.time.ZonedDateTime

data class TaskForm(
    val taskId: Int,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val taskStatus: TaskStatus,
    val registeredAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?
)