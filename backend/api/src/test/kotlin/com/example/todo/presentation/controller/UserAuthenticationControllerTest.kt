package com.example.todo.presentation.controller

import com.example.todo.application.usecase.UserAuthUseCase
import com.example.todo.presentation.controller.advice.TodoExceptionHandler
import com.example.todo.presentation.dto.request.UserLogin
import com.example.todo.presentation.dto.response.AuthenticatedResponse
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.net.URI
import kotlin.reflect.KClass

class UserAuthenticationControllerTest {

    @MockK
    private lateinit var userAuthUseCase: UserAuthUseCase

    @InjectMockKs
    private lateinit var userAuthenticationController: UserAuthenticationController

    private lateinit var mockMvc: MockMvc

    companion object {
        private val INSTANCE = URI("/v1/auth/login")
        private val objectMapper = ObjectMapper()

        @JvmStatic
        private fun verificationWithInvalidClient() = listOf(
            Arguments.of(
                // scenario of success to login.
                UserLogin("test@example.com", "test-password"),
                "Bearer token",
                false to NotImplementedError::class,
                Triple(HttpStatus.OK, MediaType.APPLICATION_JSON, AuthenticatedResponse("Bearer token")),
            ),
            Arguments.of(
                // scenario of invalid password.
                UserLogin("test@example.com", " "),
                "dummy",
                false to NotImplementedError::class,
                Triple(
                    HttpStatus.BAD_REQUEST,
                    MediaType.APPLICATION_PROBLEM_JSON,
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    }
                ),
            ),
            Arguments.of(
                // scenario of invalid email.
                UserLogin(" ", "test-password"),
                "dummy",
                false to NotImplementedError::class,
                Triple(
                    HttpStatus.BAD_REQUEST,
                    MediaType.APPLICATION_PROBLEM_JSON,
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    }
                ),
            ),
            Arguments.of(
                // scenario of user not found
                UserLogin("notfound@example.com", "test-password"),
                null,
                false to NotImplementedError::class,
                Triple(
                    HttpStatus.NOT_FOUND,
                    MediaType.APPLICATION_PROBLEM_JSON,
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        "Your account does not exist.",
                    ).apply {
                        this.instance = INSTANCE
                    }
                ),
            ),
            Arguments.of(
                // scenario of failure auth with BadCredentialsException.
                UserLogin("test@example.com", "invalid-password"),
                "dummy",
                true to BadCredentialsException::class,
                Triple(
                    HttpStatus.BAD_REQUEST,
                    MediaType.APPLICATION_PROBLEM_JSON,
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    }
                ),
            ),
        )
    }

    @BeforeEach
    fun setup() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) // because of testing ProblemDetail
        MockKAnnotations.init(this)
        mockMvc = MockMvcBuilders
            .standaloneSetup(userAuthenticationController)
            .setControllerAdvice(TodoExceptionHandler::class.java)
            .build()
    }

    @ParameterizedTest
    @MethodSource("verificationWithInvalidClient")
    fun `Verify attempt login with invalid user and expected response`(
        userLogin: UserLogin,
        token: String?,
        useCaseWillThrow: Pair<Boolean, KClass<Throwable>>,
        expected: Triple<HttpStatus, MediaType, Any>,
    ) {
        if (useCaseWillThrow.first) {
            val constructor = useCaseWillThrow.second.constructors.first()
            every {
                userAuthUseCase.attemptLogin(userLogin.email, userLogin.password)
            } throws constructor.call("Mock exception was thrown")
        } else {
            every {
                userAuthUseCase.attemptLogin(userLogin.email, userLogin.password)
            } returns token
        }
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(INSTANCE)
                .content(objectMapper.writeValueAsString(userLogin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(expected.first.value()))
            .andExpect(content().contentType(expected.second))
            .andExpect(content().json(objectMapper.writeValueAsString(expected.third)))
    }
}
