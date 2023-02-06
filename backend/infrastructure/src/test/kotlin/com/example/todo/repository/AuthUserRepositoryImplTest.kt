package com.example.todo.repository

import com.example.todo.auth.entity.AuthUser
import com.example.todo.mysql.mapper.UserAccountMapper
import com.example.todo.mysql.table.UserTable
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class AuthUserRepositoryImplTest {

    @MockK
    private lateinit var userAccountMapper: UserAccountMapper

    @InjectMockKs
    private lateinit var authUserRepositoryImpl: AuthUserRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    companion object {
        private const val VALID_EMAIL = "test@example.com"
        private const val INVALID_EMAIL = "invlid@example.com"
        private const val USER_ID = 1
        private const val NAME = "tecchu11"
        private const val PASSWORD = "test-password"
        private const val VALID_ROLE = "USER"
        private const val INVALID_ROLE = "FOO"

        @JvmStatic
        private fun verificationFindExactly() = listOf(
            Arguments.of(
                VALID_EMAIL,
                UserTable(
                    userId = USER_ID,
                    name = NAME,
                    email = VALID_EMAIL,
                    password = PASSWORD,
                    role = VALID_ROLE,
                ),
                AuthUser.from(
                    userId = USER_ID,
                    email = VALID_EMAIL,
                    password = PASSWORD,
                    role = VALID_ROLE,
                ),
            ),
            Arguments.of(
                INVALID_EMAIL,
                null,
                null,
            ),
            Arguments.of(
                VALID_EMAIL,
                UserTable(
                    userId = USER_ID,
                    name = NAME,
                    email = VALID_EMAIL,
                    password = PASSWORD,
                    role = INVALID_ROLE,
                ),
                null,
            )
        )
    }

    @ParameterizedTest
    @MethodSource("verificationFindExactly")
    fun `Verify object returned find method is expected`(
        email: String,
        mockUserTable: UserTable?,
        expected: AuthUser?
    ) {
        every { userAccountMapper.find(email) } returns mockUserTable

        val actual = authUserRepositoryImpl.find(email)

        actual `should be equal to` expected
    }
}
