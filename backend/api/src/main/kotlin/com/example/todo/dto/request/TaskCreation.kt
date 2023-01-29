package com.example.todo.dto.request

import jakarta.validation.constraints.NotBlank

data class TaskCreation(
    @field:NotBlank
    val summary: String,
    val description: String?,
)
