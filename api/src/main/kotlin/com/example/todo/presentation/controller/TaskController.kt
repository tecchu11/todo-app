package com.example.todo.presentation.controller

import com.example.todo.application.usecase.TaskUseCase
import com.example.todo.domain.task.vo.Status
import com.example.todo.domain.task.vo.TaskId
import com.example.todo.presentation.config.validation.TaskID
import com.example.todo.presentation.dto.request.TaskCreation
import com.example.todo.presentation.dto.request.TaskEdit
import com.example.todo.presentation.dto.response.TaskResponse
import com.example.todo.presentation.exception.ClientException
import com.example.todo.presentation.exception.ResourceNotFoundException
import jakarta.validation.constraints.Positive
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class TaskController(
    private val taskUseCase: TaskUseCase,
) {
    companion object {
        private const val BASE_PATH = "/v1/users/{userId}"
    }

    @GetMapping("$BASE_PATH/tasks")
    fun findAll(
        @PathVariable @Positive userId: Int
    ): List<TaskResponse> {
        val tasks = taskUseCase.findTasks(userId).map {
            TaskResponse.from(it)
        }

        if (tasks.isEmpty()) {
            throw ResourceNotFoundException(
                response = "You don't own task",
                message = "Task does not exist by userid = $userId",
            )
        }
        return tasks
    }

    @GetMapping("$BASE_PATH/tasks/{taskId}")
    fun find(
        @PathVariable @Positive userId: Int,
        @PathVariable @TaskID taskId: String
    ): TaskResponse {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation task domain model by $taskId",
                cause = it,
            )
        }.getOrThrow()

        val task = taskUseCase.findTask(userId, id)?.let {
            TaskResponse.from(it)
        }

        return task ?: throw ResourceNotFoundException(
            response = "Task with $taskId doesn't exist",
            message = "Task(taskId=$taskId and userId=$userId) doesn't exist",
        )
    }

    @PostMapping("$BASE_PATH/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun issue(
        @PathVariable @Positive userId: Int,
        @RequestBody @Validated taskCreation: TaskCreation,
    ): TaskResponse {
        return taskUseCase.issueNewTask(
            userId = userId,
            summary = taskCreation.summary,
            description = taskCreation.description
        )?.run {
            TaskResponse.from(this)
        } ?: throw RuntimeException(
            "when issuing task, issue use case was return null result. But, null result was unexpected"
        )
    }

    @PutMapping("$BASE_PATH/tasks/{taskId}")
    fun edit(
        @PathVariable userId: Int,
        @PathVariable @TaskID taskId: String,
        @RequestBody @Validated taskEdit: TaskEdit,
    ): TaskResponse {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation task domain model by $taskId",
                cause = it,
            )
        }.getOrThrow()

        return taskUseCase.editTask(
            userId = userId,
            taskId = id,
            summary = taskEdit.summary,
            description = taskEdit.description,
            status = Status.valueOf(taskEdit.status),
        )?.run {
            TaskResponse.from(this)
        } ?: throw ResourceNotFoundException(
            response = "Task with $taskId doesn't exist",
            message = "Task(taskId=$taskId and userId=$userId) doesn't exist",
        )
    }

    @DeleteMapping("$BASE_PATH/tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAll(
        @PathVariable @Positive userId: Int,
    ) {
        taskUseCase.deleteTasks(userId)
    }

    @DeleteMapping("$BASE_PATH/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive userId: Int,
        @PathVariable @TaskID taskId: String,
    ) {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation task domain model by $taskId",
                cause = it,
            )
        }.getOrThrow()

        taskUseCase.deleteTask(userId, id)
    }
}
