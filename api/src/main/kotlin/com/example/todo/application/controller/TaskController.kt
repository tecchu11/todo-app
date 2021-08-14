package com.example.todo.application.controller

import com.example.todo.application.form.TaskForm
import com.example.todo.domain.entity.Task
import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.service.TaskService
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
        val tasks: List<Task> = taskService.findAll(userId)
        return ResponseData("Success", tasks.map { toForm(it) })
    }

    @PostMapping("/")
    fun register(@RequestBody taskForm: TaskForm): ResponseData<TaskForm> {
        taskService.register(toModel(taskForm))
        return ResponseData("Register Success", taskForm)

    }

    @PutMapping("/")
    fun update(@RequestBody taskForm: TaskForm): ResponseData<TaskForm> {
        taskService.update(toModel(taskForm))
        return ResponseData("Update Success", taskForm)
    }

    private fun toForm(task: Task): TaskForm {
        return TaskForm(
            task.taskId,
            task.taskSummary,
            task.taskDescription,
            task.userId,
            task.status,
            task.registeredAt,
            task.updatedAt
        )
    }

    private fun toModel(taskForm: TaskForm): Task {
        val registeredAt = taskForm.registeredAt ?: Instant.now()
        val updatedAt = taskForm.updatedAt ?: Instant.now()
        return Task(
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