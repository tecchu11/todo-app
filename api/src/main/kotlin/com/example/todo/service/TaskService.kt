package com.example.todo.service

import com.example.todo.exception.NotFoundException
import com.example.todo.mapper.TaskMapper
import com.example.todo.model.TaskModel
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskMapper: TaskMapper) {

    fun findAll(userId: Int): List<TaskModel> {

        val taskRecords = taskMapper.selectAll(userId)
        if (taskRecords.isEmpty()) throw NotFoundException("task not found")
        return taskRecords
    }

    fun register(taskModel: TaskModel) {

        taskMapper.insert(taskModel)
    }

    fun update(taskModel: TaskModel) {

        taskMapper.update(taskModel)
    }
}