package com.example.todo.config

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Duration
import java.time.temporal.ChronoUnit

@SpringBootTest
@SpringBootConfiguration
@ActiveProfiles("bearer-token-test")
@EnableConfigurationProperties(BearerTokenConfig::class)
class BearerTokenConfigTest {

    @Autowired
    lateinit var bearerTokenConfig: BearerTokenConfig

    @Test
    fun `verify configuration binding`() {
        bearerTokenConfig `should be equal to` BearerTokenConfig(
            issuer = "tecchu11",
            expireAfter = Duration.of(60 * 60 * 24, ChronoUnit.SECONDS),
            secretKey = "test",
        )
    }
}
