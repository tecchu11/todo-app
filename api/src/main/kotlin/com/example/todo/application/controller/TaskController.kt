package com.example.todo.application.controller

import com.example.todo.application.dto.TaskDto
import com.example.todo.application.dto.TaskRegistrationDto
import com.example.todo.application.dto.TaskResponseDto
import com.example.todo.application.dto.TaskUpdateDto
import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.entity.Task
import com.example.todo.domain.service.TaskService
import com.github.guepardoapps.kulid.ULID
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
    fun findAll(@PathVariable userId: Int): ResponseData<List<TaskDto>> {
        val tasks = taskService.findAll(userId).run {
            this.map {
                TaskDto(
                    it.taskId, it.taskSummary, it.taskDescription, it.userId, it.status, it.registeredAt, it.updatedAt
                )
            }
        }
        return ResponseData("message", tasks)
    }

    @PostMapping("/")
    fun register(@RequestBody taskRegistrationDto: TaskRegistrationDto): ResponseData<TaskResponseDto> {
        val registrationTask = Task(
            ULID.random(),
            taskRegistrationDto.taskSummary,
            taskRegistrationDto.taskDescription,
            taskRegistrationDto.userId,
            taskRegistrationDto.status
        )
        return registrationTask.run {
            taskService.register(this)
            ResponseData(
                "register success",
                TaskResponseDto(
                    registrationTask.taskId,
                    registrationTask.taskSummary,
                    registrationTask.taskDescription,
                    registrationTask.userId,
                    registrationTask.status
                )
            )
        }
    }

    @PutMapping("/")
    fun update(@RequestBody taskUpdateDto: TaskUpdateDto): ResponseData<TaskResponseDto> {
        val updateTask = Task(
            taskUpdateDto.taskId,
            taskUpdateDto.taskSummary,
            taskUpdateDto.taskDescription,
            taskUpdateDto.userId,
            taskUpdateDto.status
        )
        return updateTask.run {
            taskService.update(this)
            ResponseData(
                "update success",
                TaskResponseDto(
                    updateTask.taskId,
                    updateTask.taskSummary,
                    updateTask.taskDescription,
                    updateTask.userId,
                    updateTask.status
                )
            )
        }
    }
}
