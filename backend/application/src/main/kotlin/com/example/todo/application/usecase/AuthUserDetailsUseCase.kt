package com.example.todo.application.usecase

import com.example.todo.application.annnotation.UseCase
import com.example.todo.application.dto.AuthUserDetails
import com.example.todo.domain.auth.AuthUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Usecase for loading user detail.
 *
 * This class used by spring security.
 */
@UseCase
class AuthUserDetailsUseCase(
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
                AuthUserDetails(
                    id = authUser.userId.literal,
                    password = authUser.userCredential.password,
                    role = authUser.role.name,
                )
            }
        } ?: throw UsernameNotFoundException("Authenticated user does not found by passed email")
    }
}
