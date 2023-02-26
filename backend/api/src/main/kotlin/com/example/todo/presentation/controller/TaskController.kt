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
import com.example.todo.presentation.exception.ServerException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
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
@Tag(
    name = "Task API",
    description = "Provide task resources operations."
)
@ApiResponses(
    value = [
        ApiResponse(
            description = "Bad request with invalid content.",
            responseCode = "400",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = Schema(implementation = ProblemDetail::class),
                )
            ]
        ),
        ApiResponse(
            responseCode = "401",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = Schema(implementation = ProblemDetail::class),
                )
            ]
        ),
        ApiResponse(
            responseCode = "403",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = Schema(implementation = ProblemDetail::class),
                )
            ]
        ),
        ApiResponse(
            description = "Failed to operate because of internal server error. Please retry few minutes later.",
            responseCode = "500",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = Schema(implementation = ProblemDetail::class),
                )
            ]
        ),
    ]
)
@SecurityRequirement(name = "bearer-key")
class TaskController(
    private val taskUseCase: TaskUseCase,
) : BaseAuthenticatedController() {
    companion object {
        private const val BASE_PATH = "/v1/users/tasks"
    }

    @GetMapping(BASE_PATH)
    @Operation(
        summary = "Fetch all tasks.",
        description = "Fetch all tasks of authenticated user.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to fetch all tasks.",
                responseCode = "200",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = TaskResponse::class)),
                    )
                ]
            ),
            ApiResponse(
                description = "Not found tasks because user does not own.",
                responseCode = "404",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                        schema = Schema(implementation = ProblemDetail::class)
                    )
                ]
            ),
        ]
    )
    fun findAll(): List<TaskResponse> {
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

    @GetMapping("$BASE_PATH/{taskId}")
    @Operation(
        summary = "Fetch task by task_id.",
        description = "Fetch task by task_id of authenticated user.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to fetch task by task_id",
                responseCode = "200",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = TaskResponse::class),
                    )
                ]
            ),
            ApiResponse(
                description = "Not found task because user does not own task of task_id.",
                responseCode = "404",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                        schema = Schema(implementation = ProblemDetail::class)
                    )
                ]
            ),
        ]
    )
    fun find(
        @PathVariable @TaskID
        taskId: String
    ): TaskResponse {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation TaskId by $taskId",
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

    @PostMapping(BASE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Issue new task.",
        description = """Issue new task with summary and description.
            Issued task has status OPEN. 
        """,
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to issue task.",
                responseCode = "201",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = TaskResponse::class),
                    )
                ]
            ),
        ]
    )
    fun issue(
        @RequestBody @Validated
        taskCreation: TaskCreation,
    ): TaskResponse {
        return taskUseCase.issueNewTask(
            userId = userId,
            summary = taskCreation.summary,
            description = taskCreation.description
        )?.run {
            TaskResponse.from(this)
        } ?: throw ServerException(
            response = "Unexpected error was happened, please retry to request later",
            message = "When issuing task, issue use case was return null result. But, null result was unexpected",
        )
    }

    @PutMapping("$BASE_PATH/{taskId}")
    @Operation(
        summary = "Update task by task id.",
        description = "Update existed task by summary, description and status.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to update task.",
                responseCode = "200",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = TaskResponse::class),
                    )
                ]
            ),
            ApiResponse(
                description = "No existing task specified by task_id",
                responseCode = "404",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                        schema = Schema(implementation = ProblemDetail::class)
                    )
                ]
            ),
        ]
    )
    fun edit(
        @PathVariable @TaskID
        taskId: String,
        @RequestBody @Validated
        taskEdit: TaskEdit,
    ): TaskResponse {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation TaskId by $taskId",
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

    @DeleteMapping(BASE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete all tasks.",
        description = "Delete user owning all task.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to delete all tasks.",
                responseCode = "204",
            ),
        ]
    )
    fun deleteAll() {
        taskUseCase.deleteTasks(userId)
    }

    @DeleteMapping("$BASE_PATH/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete task by task_id.",
        description = "Delete user owning task by task_id.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to task by task_id.",
                responseCode = "204",
            ),
        ]
    )
    fun delete(
        @PathVariable @TaskID
        taskId: String
    ) {
        val id = kotlin.runCatching {
            TaskId.from(taskId)
        }.onFailure {
            throw ClientException(
                response = "You requested invalid taskId with $taskId",
                message = "Failed instantiation TaskId by $taskId",
                cause = it,
            )
        }.getOrThrow()

        taskUseCase.deleteTask(userId, id)
    }
}
