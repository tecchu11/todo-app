package com.example.todo.application.usecase

import com.example.todo.application.annnotation.UseCase
import com.example.todo.domain.task.TaskRepository
import com.example.todo.domain.task.entity.Task
import com.example.todo.domain.task.vo.Status
import com.example.todo.domain.task.vo.TaskId

@UseCase
class TaskUseCase(
    private val taskRepository: TaskRepository,
) {

    /**
     * Find user all task.
     */
    fun findTasks(
        userId: Int,
    ): List<Task> {
        return taskRepository.find(userId)
    }

    /**
     * Find specific user task by task id.
     */
    fun findTask(
        userId: Int,
        taskId: TaskId,
    ): Task? {
        return taskRepository.find(userId, taskId)
    }

    /**
     * Issue new user task by passed arguments.
     */
    fun issueNewTask(
        userId: Int,
        summary: String,
        description: String?,
    ): Task? {
        val task = Task.create(userId, summary, description)
        return taskRepository.save(task)
    }

    /**
     * Edit task (summary, description).
     *
     * No update by null.
     */
    fun editTask(
        userId: Int,
        taskId: TaskId,
        summary: String,
        description: String?,
        status: Status,
    ): Task? {
        val existed = taskRepository.find(userId, taskId) ?: return null
        if (summary != existed.summary) existed.editSummary(summary)
        if (status != existed.status) existed.editStatus(status)
        if (description != existed.description) description?.let { existed.editDescription(it) }
        return taskRepository.save(existed)
    }

    /**
     * Delete user all tasks.
     */
    fun deleteTasks(
        userId: Int,
    ) {
        taskRepository.delete(userId)
    }

    /**
     * Delete specific user task by task id.
     */
    fun deleteTask(
        userId: Int,
        taskId: TaskId,
    ) {
        taskRepository.delete(userId, taskId)
    }
}
