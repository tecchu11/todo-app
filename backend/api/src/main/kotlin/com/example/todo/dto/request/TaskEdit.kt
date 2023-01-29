package com.example.todo.dto.request

import com.example.todo.config.validation.TaskStatus
import jakarta.validation.constraints.NotBlank

data class TaskEdit(
    @field:NotBlank
    val summary: String,
    val description: String?,
    @field:TaskStatus
    val status: String,
)
