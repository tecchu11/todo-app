package com.example.todo.controller

import com.example.todo.controller.advice.TodoExceptionHandler
import com.example.todo.dto.request.UserLogin
import com.example.todo.dto.response.AuthenticatedResponse
import com.example.todo.usecase.UserAuthUseCase
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
                HttpStatus.OK to AuthenticatedResponse("Bearer token")
            ),
            Arguments.of(
                // scenario of invalid password.
                UserLogin("test@example.com", " "),
                "dummy",
                false to NotImplementedError::class,
                HttpStatus.BAD_REQUEST to ProblemDetail
                    .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    },
            ),
            Arguments.of(
                // scenario of invalid email.
                UserLogin(" ", "test-password"),
                "dummy",
                false to NotImplementedError::class,
                HttpStatus.BAD_REQUEST to ProblemDetail
                    .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    },
            ),
            Arguments.of(
                // scenario of user not found
                UserLogin("notfound@example.com", "test-password"),
                null,
                false to NotImplementedError::class,
                HttpStatus.NOT_FOUND to ProblemDetail
                    .forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        "Your account does not exist.",
                    ).apply {
                        this.instance = INSTANCE
                    },
            ),
            Arguments.of(
                // scenario of failure auth with BadCredentialsException.
                UserLogin("test@example.com", "invalid-password"),
                "dummy",
                true to BadCredentialsException::class,
                HttpStatus.BAD_REQUEST to ProblemDetail
                    .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Invalid request content."
                    ).apply {
                        this.instance = INSTANCE
                    },
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
        expected: Pair<HttpStatus, Any>,
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
        println(objectMapper.writeValueAsString(expected.second))
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(INSTANCE)
                .content(objectMapper.writeValueAsString(userLogin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(expected.first.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(expected.second)))
    }
}
