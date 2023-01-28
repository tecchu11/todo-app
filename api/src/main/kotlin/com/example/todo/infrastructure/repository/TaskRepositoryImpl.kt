package com.example.todo.infrastructure.repository

import com.example.todo.domain.task.TaskRepository
import com.example.todo.domain.task.entity.Task
import com.example.todo.domain.task.vo.TaskId
import com.example.todo.infrastructure.mysql.mapper.TaskMapper
import com.example.todo.infrastructure.mysql.table.TaskStatus
import com.example.todo.infrastructure.mysql.table.TaskTable
import org.springframework.stereotype.Repository

@Repository
class TaskRepositoryImpl(
    private val taskMapper: TaskMapper,
) : TaskRepository {
    override fun find(userId: Int): List<Task> {
        return taskMapper.selectAll(userId).map {
            it.toDomain()
        }
    }

    override fun find(userId: Int, id: TaskId): Task? {
        return taskMapper.selectSingle(userId, id.literal)?.toDomain()
    }

    override fun save(task: Task): Task? {
        val record = TaskTable(
            id = task.id.literal,
            userId = task.userId,
            summary = task.summary,
            description = task.description,
            status = TaskStatus.valueOf(task.status.name),
            createdAt = task.issuedTime,
            updatedAt = task.lastEditedTime,
        )
        if (taskMapper.upsert(record)) return task
        return null
    }

    override fun delete(userId: Int) {
        taskMapper.deleteAll(userId)
    }

    override fun delete(userId: Int, id: TaskId) {
        taskMapper.deleteSingle(userId, id.literal)
    }
}
