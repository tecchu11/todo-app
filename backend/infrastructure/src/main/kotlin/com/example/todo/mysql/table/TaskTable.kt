package com.example.todo.mysql.table

import com.example.todo.domain.task.entity.Task
import com.example.todo.domain.task.vo.TaskId
import java.time.Instant

data class TaskTable(
    val id: String,
    val userId: Int,
    val summary: String,
    val description: String?,
    val status: TaskStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun toDomain(): Task = Task.from(
        userId = userId,
        id = TaskId.from(id),
        summary = summary,
        description = description,
        status = status.domain,
        issuedTime = createdAt,
        lastEditedTime = updatedAt,
    )
}
