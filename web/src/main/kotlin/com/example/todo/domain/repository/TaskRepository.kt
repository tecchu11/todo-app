package com.example.todo.domain.repository

import com.example.todo.domain.model.TaskModel
import com.example.todo.domain.model.TaskRegistrationModel
import com.example.todo.domain.model.TaskUpdateModel

interface TaskRepository {

    fun findAll(userId: Int): List<TaskModel>

    fun register(taskRegistrationModel: TaskRegistrationModel): TaskModel

    fun update(taskUpdateModel: TaskUpdateModel): TaskModel
}
