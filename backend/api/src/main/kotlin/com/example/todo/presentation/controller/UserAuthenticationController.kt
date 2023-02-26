package com.example.todo.presentation.controller

import com.example.todo.application.usecase.UserAuthUseCase
import com.example.todo.presentation.dto.request.UserLogin
import com.example.todo.presentation.dto.response.AuthenticatedResponse
import com.example.todo.presentation.exception.ClientException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(
    name = "User Auth API",
    description = "Provide user auth operations."
)
@ApiResponse(
    description = "Failed to login because of internal server error. Please retry few minutes later.",
    responseCode = "500",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
            schema = Schema(implementation = ProblemDetail::class),
        )
    ]
)
class UserAuthenticationController(
    private val userAuthUseCase: UserAuthUseCase,
) {

    companion object {
        private const val BASE_PATH = "/v1/auth"
    }

    @Operation(
        summary = "Attempts login.",
        description = """Attempts login with email and password.
            If successful, you will be issued an access token valid for one day.
        """,
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Success to login.",
                responseCode = "200",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = AuthenticatedResponse::class),
                    )
                ],
            ),
            ApiResponse(
                description = "Failed to login because of bad credentials",
                responseCode = "400",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                        schema = Schema(implementation = ProblemDetail::class),
                    )
                ]
            ),
        ]
    )
    @PostMapping("$BASE_PATH/login")
    fun login(
        @Validated @RequestBody
        userLogin: UserLogin
    ): AuthenticatedResponse {
        val token = userAuthUseCase.attemptLogin(
            email = userLogin.email,
            password = userLogin.password,
        ) ?: throw ClientException(
            response = "Your account does not exist.",
            message = "User attempt to login with invalid credentials.",
        )

        return AuthenticatedResponse(token)
    }
}
