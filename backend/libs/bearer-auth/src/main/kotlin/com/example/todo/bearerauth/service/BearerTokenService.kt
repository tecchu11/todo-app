package com.example.todo.bearerauth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.todo.bearerauth.config.BearerTokenConfig
import com.example.todo.bearerauth.type.BearerToken
import com.example.todo.bearerauth.type.Payload
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import java.time.Instant

/**
 * Auth token service to generate, verify token with HMAC256.
 */
@Component
class BearerTokenService(
    private val bearerTokenConfig: BearerTokenConfig,
) {

    companion object {
        private const val ROLE_CLAIM_KEY = "role"
    }

    private val algorithm: Algorithm
        get() = Algorithm.HMAC256(bearerTokenConfig.secretKey)

    /**
     * Generate bearer token with sub, role claims and extra.
     *
     * Extra claims is optional.
     */
    fun generate(
        sub: String,
        role: String,
        extra: Map<String, String> = emptyMap()
    ): BearerToken {
        val issuedAt = Instant.now()
        issuedAt.plus(bearerTokenConfig.expireAfter)
        val jws = JWT.create()
            .withIssuer(bearerTokenConfig.issuer)
            .withSubject(sub)
            .withClaim(ROLE_CLAIM_KEY, role)
            .withIssuedAt(issuedAt)
            .withExpiresAt(issuedAt.plus(bearerTokenConfig.expireAfter))
            .withPayload(extra)
            .sign(algorithm)
        return BearerToken.create(jws)
    }

    /**
     * Verify bearer token and return client detail with [com.example.todo.bearerauth.type.Payload].
     *
     * When token is invalid, thrown [org.springframework.security.authentication.BadCredentialsException].
     */
    fun verify(token: BearerToken, vararg keys: String): Payload {
        val verifier = JWT.require(algorithm)
            .withIssuer(bearerTokenConfig.issuer)
            .build()
        val decodedJWT = kotlin.runCatching {
            verifier.verify(token.jws)
        }.getOrElse { throw BadCredentialsException("Client requested with invalid jws", it) }
        val claimsByKeys = keys.associateWith { decodedJWT.getClaim(it).asString() }
        val role = decodedJWT.getClaim(ROLE_CLAIM_KEY).asString()
        return Payload(
            iss = decodedJWT.issuer,
            sub = decodedJWT.subject,
            role = role,
            extra = claimsByKeys,
        )
    }
}
