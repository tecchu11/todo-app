package com.example.todo.usecase

import com.example.todo.auth.AuthUserRepository
import com.example.todo.auth.entity.AuthUser
import com.example.todo.dto.AuthUserDetails
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.reflect.KClass

class AuthUserDetailsUseCaseTest {

    @MockK
    private lateinit var authUserRepository: AuthUserRepository

    @InjectMockKs
    private lateinit var authUserDetailsUseCase: AuthUserDetailsUseCase

    companion object {
        private const val VALID_EMAIL = "test@example.com"
        private val MOCK_VALID_AUTH_USER = AuthUser.from(
            userId = 1,
            email = VALID_EMAIL,
            password = "test-password",
            role = "USER",
        )
        private val EXPECTED_USERDETAILS = AuthUserDetails("1", "test-password", "USER",)
        private const val INVALID_EMAIL = "invalid@example.com"

        @JvmStatic
        private fun verificationExpectedException() = listOf(
            Arguments.of(
                // scenario of not found auth user
                INVALID_EMAIL,
                null,
                UsernameNotFoundException::class,
            ),
            Arguments.of(
                // scenario email is null
                null,
                null,
                UsernameNotFoundException::class,
            ),
        )
    }

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Verify object returned loadUserByUsername method is expected`() {
        every { authUserRepository.find(VALID_EMAIL) } returns MOCK_VALID_AUTH_USER

        val actual = authUserDetailsUseCase.loadUserByUsername(VALID_EMAIL)

        actual `should be equal to` EXPECTED_USERDETAILS
    }

    @ParameterizedTest
    @MethodSource("verificationExpectedException")
    fun `Verify when auth user not found, throws expected`(
        email: String?,
        mockAuthUser: AuthUser?,
        expected: KClass<Throwable>
    ) {
        email?.let {
            every { authUserRepository.find(it) } returns mockAuthUser
        }

        invoking { authUserDetailsUseCase.loadUserByUsername(email) } `should throw` expected
    }
}
