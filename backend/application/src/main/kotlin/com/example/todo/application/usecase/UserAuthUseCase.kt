package com.example.todo.application.usecase

import com.example.todo.application.annnotation.UseCase
import com.example.todo.application.dto.AuthUserDetails
import com.example.todo.bearerauth.service.BearerTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

/**
 * Usecase for authenticating user.
 */
@UseCase
class UserAuthUseCase(
    private val authenticationManager: AuthenticationManager,
    private val bearerTokenService: BearerTokenService,
) {

    /**
     * User attempt login with email and password.
     *
     * If user was authenticated, returning access token.
     */
    fun attemptLogin(email: String, password: String): String? {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        ).principal as? AuthUserDetails // returning User class. See UserDetailUseCase

        return auth?.let {
            bearerTokenService.generate(
                sub = it.username,
                // User can only have one roll.
                role = it.authorities.first().toString(),
            ).value
        }
    }
}
