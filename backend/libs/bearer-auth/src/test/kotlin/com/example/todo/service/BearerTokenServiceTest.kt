package com.example.todo.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.todo.config.BearerTokenConfig
import com.example.todo.type.BearerToken
import com.example.todo.type.Payload
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.security.authentication.BadCredentialsException
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class BearerTokenServiceTest {

    data class Fixture(
        val data: BearerToken,
        val expected: Any,
        val assertion: (Any, Any) -> Unit,
    )

    @MockK
    private lateinit var bearerTokenConfig: BearerTokenConfig

    @InjectMockKs
    private lateinit var bearerTokenService: BearerTokenService

    companion object {
        private val testConfig = BearerTokenConfig(
            issuer = "tecchu11",
            expireAfter = Duration.of(Int.MAX_VALUE.toLong(), ChronoUnit.SECONDS),
            secretKey = "test",
        )
        private const val SUB = "test client"
        private const val ROLE = "TEST"
        private const val ROLE_CLAIM_NAME = "role"
        private const val NOW = 1675335600000L // 2023-02-02T11:00:00Z
        private val fixed = Clock.fixed(
            Instant.ofEpochMilli(NOW),
            ZoneId.of("Asia/Tokyo")
        )

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun verifyGenerate() = listOf(
            Arguments.of(SUB, ROLE, emptyMap<String, String>()),
            Arguments.of(SUB, ROLE, mapOf("id" to "test")),
        )

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun verifyVerify() = listOf(
            Arguments.of(
                Fixture(
                    data = BearerToken.from(
                        """Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
                            |.eyJzdWIiOiJ0ZXN0IGNsaWVudCIsInJvbGUiOiJU
                            |RVNUIiwiaXNzIjoidGVjY2h1MTEiLCJleHAiOjM4MjI4MTkyNDcsImlhdCI6MTY3NTMzNTYwMH0
                            |._bXaDwBWWNqlyY8djfuqjppBMCZzROpBAFtpdnyRUcY"""
                            .trimMargin()
                            .replace("\n", "")
                    ),
                    expected = Payload(testConfig.issuer, SUB, ROLE, emptyMap()),
                    assertion = { a, e -> assertThat(a).isEqualTo(e) }
                )
            ),
            Arguments.of(
                Fixture(
                    data = BearerToken.from(
                        """Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
                            |.eyJzdWIiOiJ0ZXN0IGNsaWVudCIsInJvbGUiOiJU
                            |RVNUIiwiaXNzIjoidGVjY2h1MTEiLCJpZCI6InRlc
                            |3QiLCJleHAiOjM4MjI4MTkyNDcsImlhdCI6MTY3NTMzNTYwMH0
                            |.9fWWMYPVqgM-97z9rvxXOrNlHFjT39xxILqPPN_gc4Y"""
                            .trimMargin()
                            .replace("\n", "")
                    ),
                    expected = Payload(testConfig.issuer, SUB, ROLE, mapOf("id" to "test")),
                    assertion = { a, e -> assertThat(a).isEqualTo(e) }
                )
            ),
            Arguments.of(
                Fixture(
                    data = BearerToken.from("Bearer Invalid"),
                    expected = BadCredentialsException::class.java,
                    assertion = { a, e ->
                        assertThat(a as Throwable)
                            .isInstanceOf(e as Class<*>)
                            .hasMessage("Client requested with invalid jws")
                    }
                )
            ),
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Clock::class)
        every { bearerTokenConfig.issuer } returns testConfig.issuer
        every { bearerTokenConfig.expireAfter } returns testConfig.expireAfter
        every { bearerTokenConfig.secretKey } returns testConfig.secretKey
        every { Clock.systemUTC() } returns fixed
        every { Instant.now() } returns Instant.ofEpochMilli(NOW)
    }

    @ParameterizedTest
    @MethodSource("verifyGenerate")
    fun `verify generate bearer token and exactly contains each fields`(
        sub: String,
        role: String,
        extra: Map<String, String>
    ) {
        val bearerToken = bearerTokenService.generate(sub, role, extra)
        println(bearerToken)

        val decoded = JWT.require(Algorithm.HMAC256(bearerTokenConfig.secretKey))
            .withIssuer(bearerTokenConfig.issuer)
            .build()
            .verify(bearerToken.jws)

        assertThat(decoded.issuer).isEqualTo(bearerTokenConfig.issuer)
        assertThat(decoded.subject).isEqualTo(sub)
        assertThat(decoded.getClaim(ROLE_CLAIM_NAME).asString()).isEqualTo(role)
        assertThat(decoded.issuedAt).isEqualTo(Instant.now())
        assertThat(decoded.expiresAt).isEqualTo(Instant.now().plus(bearerTokenConfig.expireAfter))
        extra.forEach {
            assertThat(decoded.getClaim(it.key).asString()).isEqualTo(it.value)
        }
    }

    @ParameterizedTest
    @MethodSource("verifyVerify")
    fun `verify bearer token verification`(fixture: Fixture) {
        val payload = if (fixture.expected is Payload && fixture.expected.extra.isNotEmpty()) {
            bearerTokenService.verify(fixture.data, "id")
        } else {
            catchThrowable {
                bearerTokenService.verify(fixture.data)
            } ?: bearerTokenService.verify(fixture.data)
        }

        fixture.assertion(payload, fixture.expected)
    }
}
