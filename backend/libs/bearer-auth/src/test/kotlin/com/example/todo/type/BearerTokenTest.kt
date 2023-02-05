package com.example.todo.type

import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with message`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BearerTokenTest {
    companion object {
        private const val VALID_FORMAT_TOKEN_VALUE = "Bearer token"
        private const val EXPECTED_JWS = "token"
        private val EXPECTED_EXCEPTION = IllegalArgumentException::class
        private const val EXPECTED_MESSAGE = "This is not bearer format."
    }

    @Test
    fun `Verify success instantiation via from method with valid token format and expected jws`() {
        val actual = BearerToken.from(VALID_FORMAT_TOKEN_VALUE)

        actual.jws `should be equal to` EXPECTED_JWS
    }

    @ParameterizedTest
    @ValueSource(strings = ["Bearer", ""])
    fun `Verify from method throw IllegalArgumentsException with invalid token format`(tokenValue: String) {
        invoking {
            BearerToken.from(tokenValue)
        } `should throw` EXPECTED_EXCEPTION `with message` EXPECTED_MESSAGE
    }
}
