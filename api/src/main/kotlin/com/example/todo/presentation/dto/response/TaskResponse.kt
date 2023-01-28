package com.example.todo.presentation.dto.response

import com.example.todo.domain.task.entity.Task
import com.example.todo.presentation.config.extension.TimeZone
import com.example.todo.presentation.config.extension.toLocalDateTime
import java.time.LocalDateTime

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
        /** Create from domain */
        fun from(task: Task) = TaskResponse(
            userId = task.userId,
            taskId = task.id.literal,
            summary = task.summary,
            description = task.description,
            createdAt = task.issuedTime.toLocalDateTime(TimeZone.jst),
            updatedAt = task.lastEditedTime.toLocalDateTime(TimeZone.jst),
            status = task.status.name,
        )
    }
}
