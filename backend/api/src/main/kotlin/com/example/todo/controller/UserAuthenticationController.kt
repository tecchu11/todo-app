package com.example.todo.controller

import com.example.todo.dto.request.UserLogin
import com.example.todo.dto.response.AuthenticatedResponse
import com.example.todo.exception.ResourceNotFoundException
import com.example.todo.usecase.AuthenticationUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserAuthenticationController(
    private val authenticationUseCase: AuthenticationUseCase,
) {

    companion object {
        private const val BASE_PATH = "/v1/auth"
    }

    /**
     * Attempt login with user email and password.
     */
    @PostMapping("$BASE_PATH/login")
    fun login(@Validated @RequestBody userLogin: UserLogin): AuthenticatedResponse {
        val token = authenticationUseCase.attemptLogin(
            email = userLogin.email,
            password = userLogin.password,
        ) ?: throw ResourceNotFoundException(
            response = "Your account does not exist.",
            message = "User attempt to login with invalid credentials.",
        )

        return AuthenticatedResponse(token)
    }
}
