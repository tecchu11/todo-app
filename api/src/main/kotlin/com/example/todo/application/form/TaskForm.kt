package com.example.todo.application.form

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import com.example.todo.domain.enumration.TaskStatus
import com.github.guepardoapps.kulid.ULID
import java.time.ZonedDateTime

data class TaskForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus,
    val registeredAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
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

data class TaskResponseForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
) {
    companion object {
        fun fromTask(task: Task): TaskResponseForm = TaskResponseForm(
            task.taskId,
            task.taskSummary,
            task.taskDescription,
            task.userId,
            task.status
        )
    }
}

data class TaskRegistrationForm(
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
) {
    companion object {
        fun toTask(taskRegistrationForm: TaskRegistrationForm): Task = Task(
            ULID.random(),
            taskRegistrationForm.taskSummary,
            taskRegistrationForm.taskDescription,
            taskRegistrationForm.userId,
            taskRegistrationForm.status
        )
    }
}

data class TaskUpdateForm(
    val taskId: String,
    val taskSummary: String,
    val taskDescription: String?,
    val userId: Int,
    val status: TaskStatus
) {
    companion object {
        fun toTask(taskUpdateForm: TaskUpdateForm): Task = Task(
            taskUpdateForm.taskId,
            taskUpdateForm.taskSummary,
            taskUpdateForm.taskDescription,
            taskUpdateForm.userId,
            taskUpdateForm.status
        )
    }
}
