package com.example.todo.usecase

import com.example.todo.dto.AuthUserDetails
import com.example.todo.service.BearerTokenService
import com.example.todo.type.BearerToken
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class UserAuthUseCaseTest {

    @MockK
    private lateinit var authenticationManager: AuthenticationManager

    @MockK
    private lateinit var bearerTokenService: BearerTokenService

    @InjectMockKs
    private lateinit var userAuthUseCase: UserAuthUseCase

    companion object {
        private val VALID_USER = "test@example.com" to "test-password"
        private const val MOCKED_USER_ID = "1"
        private const val MOCKED_USER_ROLE = "USER"
        private const val MOCKED_USER_PASSWORD = "test-password"
        private val EXPECTED_USERDETAILS = AuthUserDetails(
            id = MOCKED_USER_ID,
            password = MOCKED_USER_PASSWORD,
            role = MOCKED_USER_ROLE,
        )
        private val MOCK_BEARER_TOKEN = BearerToken.from("Bearer token")
        private const val EXPECTED_TOKEN = "Bearer token"
        private val INVALID_USER = "invalid@example.com" to "invalid-password"
        @JvmStatic
        private fun verificationGenerateExactly() = listOf(
            Arguments.of(
                VALID_USER,
                EXPECTED_USERDETAILS,
                MOCK_BEARER_TOKEN.value,
                EXPECTED_TOKEN,
            ),
            Arguments.of(
                INVALID_USER,
                null,
                "Bearer dummy",
                null,
            )
        )
    }

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    /**
     * *NOTE* ParameterizedTest does not support kotlin value class.
     * See [junit5 issue](https://github.com/junit-team/junit5/issues/2703)
     */
    @ParameterizedTest
    @MethodSource("verificationGenerateExactly")
    fun `Verify success attemptLogin with valid user`(
        user: Pair<String, String>,
        expectedUserDetails: AuthUserDetails?,
        mockBearerToken: String,
        expectedGeneratedToken: String?,
    ) {
        every {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.first, user.second)
            ).principal
        } returns expectedUserDetails
        every {
            bearerTokenService.generate(any(), any())
        } returns BearerToken.from(mockBearerToken)

        val actual = userAuthUseCase.attemptLogin(user.first, user.second)

        actual `should be equal to` expectedGeneratedToken
    }
}
