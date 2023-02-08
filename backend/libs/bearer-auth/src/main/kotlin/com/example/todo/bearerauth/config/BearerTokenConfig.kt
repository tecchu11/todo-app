package com.example.todo.bearerauth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.convert.DurationUnit
import java.time.Duration
import java.time.temporal.ChronoUnit

@ConfigurationProperties("libs.bearer-token")
data class BearerTokenConfig(
    val issuer: String,
    @DurationUnit(ChronoUnit.SECONDS)
    val expireAfter: Duration,
    val secretKey: String,
)
