package com.example.todo.usecase

import com.example.todo.annnotation.UseCase
import com.example.todo.auth.AuthUserRepository
import com.example.todo.service.BearerTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Usecase for authenticating user.
 */
@UseCase
class AuthenticationUseCase(
    private val authenticationManager: AuthenticationManager,
    private val bearerTokenService: BearerTokenService,
) {

    /**
     * Authenticate user by email and password.
     *
     * If user was authenticated, returning access token.
     */
    fun authenticate(email: String, password: String): String? {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        ).principal as User // returning User class. see UserDetailUseCase

        return bearerTokenService.generate(
            sub = auth.username,
            // User can only have one roll.
            role = auth.authorities.first().toString(),
        ).value
    }
}

/**
 * Usecase for loading user detail.
 *
 * This class used by spring security.
 */
@UseCase
class AuthUserDetailUseCase(
    private val authUserRepository: AuthUserRepository,
) : UserDetailsService {

    /**
     * Load user by email for spring security.
     *
     * If email or AuthUser is null, throw [org.springframework.security.core.userdetails.UsernameNotFoundException].
     */
    override fun loadUserByUsername(email: String?): UserDetails {
        return email?.let {
            authUserRepository.find(it)?.let { authUser ->
                User(
                    authUser.userId.literal,
                    authUser.userCredential.password,
                    AuthorityUtils.createAuthorityList(authUser.role.name)
                )
            }
        } ?: throw UsernameNotFoundException("Authenticated user does not found by passed email")
    }
}
