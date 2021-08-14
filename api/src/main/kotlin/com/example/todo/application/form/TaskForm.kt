package com.example.todo.application.form

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import com.example.todo.domain.enumration.TaskStatus
import com.github.guepardoapps.kulid.ULID
import java.time.Instant

data class TaskForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: Instant,
    val updatedAt: Instant
) {
    constructor(task: TaskEntity) : this(
        task.taskId,
        task.taskSummary,
        task.taskDescription,
        task.userId,
        task.status,
        task.registeredAt,
        task.updatedAt
    )
}

data class TaskRegistrationForm(
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
) {
    fun toTask(): Task = Task(
        ULID.random(),
        this.taskSummary,
        this.taskDescription,
        this.userId,
        this.status
    )
}

data class TaskUpdateForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
) {
    fun toTask(): Task = Task(
        this.taskId,
        this.taskSummary,
        this.taskDescription,
        this.userId,
        this.status
    )
}
