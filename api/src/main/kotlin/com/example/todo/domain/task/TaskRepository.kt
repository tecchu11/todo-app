package com.example.todo.domain.task

import com.example.todo.domain.task.entity.Task
import com.example.todo.domain.task.vo.TaskId

/**
 * CRUD for domain entity of task
 */
interface TaskRepository {

    /**
     * Find all user tasks
     */
    fun find(userId: Int): List<Task>

    /**
     * Find specific user task
     */
    fun find(userId: Int, id: TaskId): Task?

    /**
     * Save specific user task
     */
    fun save(task: Task): Task?

    /**
     * Delete all user tasks
     */
    fun delete(userId: Int)

    /**
     * Delete specific user task
     */
    fun delete(userId: Int, id: TaskId)
}
