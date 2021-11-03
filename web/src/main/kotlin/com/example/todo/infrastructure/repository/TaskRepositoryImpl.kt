package com.example.todo.infrastructure.repository

import com.example.todo.domain.model.TaskModel
import com.example.todo.domain.model.TaskRegistrationModel
import com.example.todo.domain.model.TaskUpdateModel
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.dto.TaskRegistrationDto
import com.example.todo.dto.TaskUpdateDto
import com.example.todo.exceptions.NotFoundException
import com.example.todo.infrastructure.config.TaskApiProperty
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import org.springframework.web.util.UriComponentsBuilder

@Repository
class TaskRepositoryImpl(
    private val taskApiRestTemplate: RestTemplate,
    private val taskApiProperty: TaskApiProperty
) : TaskRepository {

    override fun findAll(userId: Int): List<TaskModel> {
        val uri = UriComponentsBuilder.fromUri(taskApiProperty.uri)
            .path(userId.toString())
            .build()
            .toUri()
        val response = taskApiRestTemplate.getForObject(
            uri,
            Array<TaskModel>::class.java
        )
        return response?.toList() ?: throw NotFoundException("Task not found by user id = $userId")
    }

    override fun register(taskRegistrationModel: TaskRegistrationModel): TaskModel {
        val task = TaskRegistrationDto(
            taskRegistrationModel.taskSummary,
            taskRegistrationModel.taskDescription,
            taskRegistrationModel.userId,
            taskRegistrationModel.status
        )
        return taskApiRestTemplate.postForObject(
            taskApiProperty.uri,
            task
        )
    }

    override fun update(taskUpdateModel: TaskUpdateModel): TaskModel {
        val task = TaskUpdateDto(
            taskUpdateModel.taskId,
            taskUpdateModel.taskSummary,
            taskUpdateModel.taskDescription,
            taskUpdateModel.userId,
            taskUpdateModel.status
        )
        return taskApiRestTemplate.exchange(
            RequestEntity.put(taskApiProperty.uri).body(task),
            TaskModel::class.java
        ).body ?: throw NotFoundException("")
    }
}
