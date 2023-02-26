package com.example.todo.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request body when issuing new task.")
data class TaskCreation(
    @field:NotBlank
    val summary: String,
    val description: String?,
)
