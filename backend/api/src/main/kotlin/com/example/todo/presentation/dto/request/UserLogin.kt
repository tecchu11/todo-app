package com.example.todo.presentation.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * Request body of user login.
 */
data class UserLogin(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)
