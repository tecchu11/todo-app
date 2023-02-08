package com.example.todo.presentation.controller

import com.example.todo.dto.AuthUserDetails
import com.example.todo.presentation.exception.ClientException
import org.springframework.security.core.context.SecurityContextHolder

open class BaseAuthenticatedController {

    val userId: Int
        get() {
            return loadUserId()
        }

    private fun loadUserId(): Int {
        val authUser = SecurityContextHolder.getContext()?.authentication?.principal as? AuthUserDetails

        return authUser?.let {
            kotlin.runCatching {
                it.username.toInt()
            }.getOrNull()
        } ?: throw ClientException(
            message = "User requested with invalid userId(${authUser?.username}) but this exception was unexpected.",
            response = "Invalid request content.",
        )
    }
}
