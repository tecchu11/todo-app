package com.example.com.domain.shared.vo

import com.example.todo.domain.shared.vo.UserId
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with message`
import org.junit.jupiter.api.Test

class UserIdTest {

    companion object {
        private const val USER_ID_VALID = 1
        private const val USER_ID_INVALID = 0
        private const val EXPECTED_USER_ID_VALUE = 1
        private const val EXPECTED_USER_ID_LITERAL = "1"
        private const val EXPECTED_EXCEPTION_MESSAGE = "Invalid user id."
    }

    @Test
    fun `Verify success instantiation via from method and equal value to expected`() {
        val actual = UserId.from(USER_ID_VALID)

        actual.value `should be equal to` EXPECTED_USER_ID_VALUE
    }

    @Test
    fun `Verify user id with invalid value throws IllegalArgumentException`() {
        invoking {
            UserId.from(USER_ID_INVALID)
        } `should throw` IllegalArgumentException::class `with message` EXPECTED_EXCEPTION_MESSAGE
    }

    @Test
    fun `Verify literal exactly`() {
        UserId.from(USER_ID_VALID).literal `should be equal to` EXPECTED_USER_ID_LITERAL
    }
}
