package com.example.todo.application.controller

import com.example.todo.application.form.TaskForm
import com.example.todo.application.form.TaskRegistrationForm
import com.example.todo.application.form.TaskUpdateForm
import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.entity.TaskEntity
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
        val tasks: List<TaskEntity> = taskService.findAll(userId)
        return ResponseData("Success", tasks.map { TaskForm(it) })
    }

    @PostMapping("/")
    fun register(@RequestBody taskRegistrationForm: TaskRegistrationForm): ResponseData<TaskRegistrationForm> {
        taskService.register(taskRegistrationForm.toTask())
        return ResponseData("Register Success", taskRegistrationForm)

    }

    @PutMapping("/")
    fun update(@RequestBody taskUpdateForm: TaskUpdateForm): ResponseData<TaskUpdateForm> {
        taskService.update(taskUpdateForm.toTask())
        return ResponseData("Update Success", taskUpdateForm)
    }
}