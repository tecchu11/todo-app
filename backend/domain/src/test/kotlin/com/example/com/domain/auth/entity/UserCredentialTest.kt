package com.example.com.domain.auth.entity

import com.example.todo.domain.auth.entity.UserCredential
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with message`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass

class UserCredentialTest {

    companion object {
        private const val VALID_EMAIL = "test@example.com"
        private const val VALID_PASSWORD = "test-password"
        private const val BLANK = " "
        private val EXPECTED_USER_CREDENTIAL = UserCredential(VALID_EMAIL, VALID_PASSWORD)
        private const val EXPECTED_EXCEPTION_MESSAGE = "Email and password are not allowed to be blank."
        private const val EXPECTED_TOSTRING = "UserCredential(email=**, password=**)"

        @JvmStatic
        private fun verificationWithInvalidEmailOrPassword() = listOf(
            Arguments.of(BLANK, VALID_PASSWORD, IllegalArgumentException::class to EXPECTED_EXCEPTION_MESSAGE),
            Arguments.of(VALID_EMAIL, BLANK, IllegalArgumentException::class to EXPECTED_EXCEPTION_MESSAGE),
        )
    }

    @Test
    fun `Verify success instantiation via from method and equal to expected`() {
        val actual = UserCredential.from(VALID_EMAIL, VALID_PASSWORD)

        actual `should be equal to` EXPECTED_USER_CREDENTIAL
    }

    @ParameterizedTest
    @MethodSource("verificationWithInvalidEmailOrPassword")
    fun `Verify from method with blank email or password throws IllegalArgumentException`(
        email: String,
        password: String,
        expectedException: Pair<KClass<Throwable>, String>,
    ) {
        invoking {
            UserCredential.from(email, password)
        } `should throw` expectedException.first `with message` expectedException.second
    }

    @Test
    fun `verify toString return masked value`() {
        UserCredential.from(VALID_EMAIL, VALID_PASSWORD).toString() `should be equal to` EXPECTED_TOSTRING
    }
}
