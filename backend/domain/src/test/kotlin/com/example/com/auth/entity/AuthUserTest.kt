package com.example.com.auth.entity

import com.example.todo.auth.entity.AuthUser
import com.example.todo.auth.entity.UserCredential
import com.example.todo.auth.vo.UserRole
import com.example.todo.shared.vo.UserId
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass

class AuthUserTest {

    companion object {
        private const val VALID_ID = 1
        private const val VALID_EMAIL = "test@example.com"
        private const val VALID_PASSWORD = "test-password"
        private const val VALID_ROLE = "USER"
        private const val INVALID_ID = -1
        private const val BLANK = " "
        private const val INVALID_ROLE = "FOO"
        private val EXPECTED_AUTH_USER = AuthUser(
            userId = UserId.from(1),
            userCredential = UserCredential("test@example.com", "test-password"),
            role = UserRole.USER
        )

        @JvmStatic
        private fun verificationWithInvalidArg() = listOf(
            Arguments.of(INVALID_ID, VALID_EMAIL, VALID_PASSWORD, VALID_ROLE, IllegalArgumentException::class),
            Arguments.of(INVALID_ID, BLANK, VALID_PASSWORD, VALID_ROLE, IllegalArgumentException::class),
            Arguments.of(INVALID_ID, VALID_EMAIL, BLANK, VALID_ROLE, IllegalArgumentException::class),
            Arguments.of(INVALID_ID, VALID_EMAIL, VALID_PASSWORD, INVALID_ROLE, IllegalArgumentException::class),
        )
    }

    @Test
    fun `Verify success instantiation via from method`() {
        val actual = AuthUser.from(
            userId = VALID_ID,
            email = VALID_EMAIL,
            password = VALID_PASSWORD,
            role = VALID_ROLE,
        )

        actual `should be equal to` EXPECTED_AUTH_USER
    }

    @ParameterizedTest
    @MethodSource("verificationWithInvalidArg")
    fun `Verify from method with invalid id, email, password or role will throw expected`(
        id: Int,
        email: String,
        password: String,
        role: String,
        expected: KClass<Throwable>
    ) {
        invoking {
            AuthUser.from(
                userId = id, email = email, password = password, role = role
            )
        } `should throw` expected
    }
}
