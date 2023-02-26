package com.example.todo.presentation.dto.request

import com.example.todo.presentation.config.validation.TaskStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request body when updating existed task.")
data class TaskEdit(
    @field:NotBlank
    val summary: String,
    val description: String?,
    @field:TaskStatus
    val status: String,
)
