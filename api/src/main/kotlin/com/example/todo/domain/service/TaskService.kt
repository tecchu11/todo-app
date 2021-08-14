package com.example.todo.domain.service

import com.example.todo.domain.exception.NotFoundException
import com.example.todo.infrastructure.mapper.TaskMapper
import com.example.todo.domain.entity.Task
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskMapper: TaskMapper) {

    fun findAll(userId: Int): List<Task> {

        val taskRecords = taskMapper.selectAll(userId)
        if (taskRecords.isEmpty()) throw NotFoundException("task not found")
        return taskRecords
    }

    fun register(task: Task) {

        taskMapper.insert(task)
    }

    fun update(task: Task) {

        if (!taskMapper.update(task)) throw NotFoundException("Update target not found")
    }
}