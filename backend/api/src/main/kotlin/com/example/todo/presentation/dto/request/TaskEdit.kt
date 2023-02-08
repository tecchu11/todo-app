package com.example.todo.presentation.dto.request

import com.example.todo.presentation.config.validation.TaskStatus
import jakarta.validation.constraints.NotBlank

data class TaskEdit(
    @field:NotBlank
    val summary: String,
    val description: String?,
    @field:TaskStatus
    val status: String,
)
