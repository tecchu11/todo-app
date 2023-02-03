package com.example.todo.type

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class BearerTokenTest {

    data class Fixture(
        val data: String,
        val expected: Any,
        val assertion: (Any, Any) -> Unit,
    )

    companion object {
        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun verifyInstantiationParam() = listOf(
            Arguments.of(
                Fixture(
                    data = "${BearerToken.PREFIX}token",
                    expected = "token",
                    assertion = { a, e ->
                        assertThat(a).isEqualTo(e)
                    }
                )
            ),
            Arguments.of(
                Fixture(
                    data = "NOT Bearer",
                    expected = IllegalArgumentException::class.java,
                    assertion = { a, e ->
                        assertThat(a as Throwable)
                            .isInstanceOf(e as Class<*>)
                            .hasMessage("This is not bearer format.")
                    }
                )
            )
        )
    }

    @ParameterizedTest
    @MethodSource("verifyInstantiationParam")
    fun `Verify instantiation`(fixture: Fixture) {
        val actual = catchThrowable {
            BearerToken.from(fixture.data)
        } ?: BearerToken.from(fixture.data).jws
        fixture.assertion(actual, fixture.expected)
    }
}
