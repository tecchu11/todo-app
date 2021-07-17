package com.example.todo.controller

import com.example.todo.form.TaskForm
import com.example.todo.model.TaskModel
import com.example.todo.reponse.ResponseData
import com.example.todo.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.ZoneId

@RestController
@RequestMapping("/v1/todo")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping("/{userId}")
    fun findAll(@PathVariable userId: Int): ResponseData<List<TaskForm>> {
        val taskModels: List<TaskModel> = taskService.findAll(userId)
        return ResponseData("Success", taskModels.map { toForm(it) })
    }

    @PostMapping("/{userId}")
    fun register(
        @PathVariable userId: Int,
        @RequestBody taskForm: TaskForm
    ): ResponseData<TaskForm> {

        if (taskForm.userId != userId) throw IllegalArgumentException("${taskForm.userId} is different form $userId")
        taskService.register(toModel(taskForm))
        return ResponseData("Register Success", taskForm)

    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable userId: Int,
        @RequestBody taskForm: TaskForm
    ): ResponseData<TaskForm> {

        if (taskForm.userId != userId) throw IllegalArgumentException("${taskForm.userId} is different form $userId")
        taskService.update(toModel(taskForm))
        return ResponseData("Update Success", taskForm)
    }

    private fun toForm(taskModel: TaskModel): TaskForm {

        // Assert non-null(!!) because taskModel's registeredAt, updatedAt is always non-null
        return TaskForm(
            taskModel.taskId,
            taskModel.taskSummary,
            taskModel.taskDescription,
            taskModel.userId,
            taskModel.taskStatus,
            taskModel.registeredAt!!.atZone(ZoneId.systemDefault()),
            taskModel.updatedAt!!.atZone(ZoneId.systemDefault())
        )
    }

    private fun toModel(taskForm: TaskForm): TaskModel {

        val registeredAt: Instant? = if (taskForm.registeredAt == null) {
            null
        } else {
            taskForm.registeredAt.toInstant()
        }
        val updatedAt: Instant? = if (taskForm.updatedAt == null) {
            null
        } else {
            taskForm.updatedAt.toInstant()
        }
        return TaskModel(
            taskForm.taskId,
            taskForm.taskSummary,
            taskForm.taskDescription,
            taskForm.userId,
            taskForm.taskStatus,
            registeredAt,
            updatedAt
        )
    }
}