package com.example.todo.presentation.dto.response

import com.example.todo.presentation.config.extension.JavaTime
import com.example.todo.presentation.config.extension.toLocalDateTime
import com.example.todo.task.entity.Task
import java.time.LocalDateTime

/**
 * Indicate response of task resources.
 */
data class TaskResponse(
    val userId: Int,
    val taskId: String,
    val summary: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val status: String,
) {
    companion object {
        /** Create from domain. */
        fun from(task: Task) = TaskResponse(
            userId = task.userId,
            taskId = task.id.literal,
            summary = task.summary,
            description = task.description,
            createdAt = task.issuedTime.toLocalDateTime(JavaTime.jst),
            updatedAt = task.lastEditedTime.toLocalDateTime(JavaTime.jst),
            status = task.status.name,
        )
    }
}
