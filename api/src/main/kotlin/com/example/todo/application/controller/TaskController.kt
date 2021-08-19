package com.example.todo.application.controller

import com.example.todo.application.form.TaskForm
import com.example.todo.application.form.TaskRegistrationForm
import com.example.todo.application.form.TaskResponseForm
import com.example.todo.application.form.TaskUpdateForm
import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/todo")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping("/{userId}")
    fun findAll(@PathVariable userId: Int): ResponseData<List<TaskForm>> {
        return taskService
            .findAll(userId)
            .run {
                ResponseData("Success", this.map { TaskForm(it) })
            }
    }

    @PostMapping("/")
    fun register(@RequestBody taskRegistrationForm: TaskRegistrationForm): ResponseData<TaskResponseForm> {
        return TaskRegistrationForm
            .toTask(taskRegistrationForm)
            .run {
                taskService.register(this)
                ResponseData("Register Success", TaskResponseForm.fromTask(this))
            }
    }

    @PutMapping("/")
    fun update(@RequestBody taskUpdateForm: TaskUpdateForm): ResponseData<TaskResponseForm> {
        return TaskUpdateForm
            .toTask(taskUpdateForm)
            .run {
                taskService.update(this)
                ResponseData("Update Success", TaskResponseForm.fromTask(this))
            }
    }
}
