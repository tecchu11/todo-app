package com.example.todo.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * Request body of user login.
 */
@Schema(description = "Request body when attempting login.")
data class UserLogin(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)
