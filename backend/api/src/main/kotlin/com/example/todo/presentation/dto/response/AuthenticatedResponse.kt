package com.example.todo.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Access token for auth user.
 */
@Schema(description = "Access token for auth user. Access token valid for one day.")
data class AuthenticatedResponse(
    val token: String,
)
