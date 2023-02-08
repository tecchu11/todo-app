package com.example.todo.presentation.controller

import com.example.todo.dto.AuthUserDetails
import com.example.todo.presentation.exception.ClientException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with message`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import kotlin.reflect.KClass

class BaseAuthenticatedControllerTest {

    @MockK
    private lateinit var authentication: Authentication

    @MockK
    private lateinit var securityContext: SecurityContext

    @InjectMockKs
    private lateinit var baseAuthenticatedController: BaseAuthenticatedController

    companion object {
        private val MOCKED_AUTH_USERDETAILS = AuthUserDetails(id = "1", password = "", role = "USER")
        private const val EXPECTED_USER_ID = 1

        @JvmStatic
        private fun verificationWillThrow() = listOf(
            Arguments.of(
                User("1", "invalid sub type", AuthorityUtils.createAuthorityList("USER")),
                ClientException::class,
                "User requested with invalid userId(null) but this exception was unexpected."
            ),
            Arguments.of(
                AuthUserDetails("invalid", "id is permitted number", "USER"),
                ClientException::class,
                "User requested with invalid userId(invalid) but this exception was unexpected.",
            ),
        )
    }

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(SecurityContextHolder::class)
    }

    @Test
    fun `Verify userId is expected`() {
        every { SecurityContextHolder.getContext() } returns securityContext
        every { securityContext.authentication } returns authentication
        every { authentication.principal } returns MOCKED_AUTH_USERDETAILS

        baseAuthenticatedController.userId `should be equal to` EXPECTED_USER_ID
    }

    @ParameterizedTest
    @MethodSource("verificationWillThrow")
    fun `Verify request with invalid content will throw expected`(
        mockUserDetails: UserDetails,
        expectedThrowable: KClass<Throwable>,
        expectedMessage: String,
    ) {
        every { SecurityContextHolder.getContext() } returns securityContext
        every { securityContext.authentication } returns authentication
        every { authentication.principal } returns mockUserDetails

        invoking {
            baseAuthenticatedController.userId
        } `should throw` expectedThrowable `with message` expectedMessage
    }
}
