package com.example.todo.presentation.dto.response

import com.example.todo.domain.task.entity.Task
import com.example.todo.presentation.config.extension.JavaTime
import com.example.todo.presentation.config.extension.toLocalDateTime
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * Indicate resource of task as response.
 */
@Schema(description = "Indicate resource of task as response.")
data class TaskResponse(
    @field:JsonProperty("user_id")
    @Schema(description = "Unique identifier of user owning this task.")
    val userId: Int,
    @field:JsonProperty("task_id")
    @Schema(description = "Unique identifier of this task.")
    val taskId: String,
    val summary: String,
    @Schema(description = "Description is nullable value.")
    val description: String?,
    @field:JsonProperty("created_at")
    @Schema(description = "created_at format is yyyy-MM-ddThh:mm:ss")
    val createdAt: LocalDateTime,
    @field:JsonProperty("updated_at")
    @Schema(description = "updated_at format is yyyy-MM-ddThh:mm:ss")
    val updatedAt: LocalDateTime,
    @Schema(description = "This value can be OPEN, WIP, or CLOSE.")
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
