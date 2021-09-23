package com.example.todo.domain.service

import com.example.todo.domain.configuration.TodoAppApiKey
import com.example.todo.domain.enumration.UserRole
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service

@Service
class TodoAuthenticationUserDetailService(
    private val todoAppApiKey: TodoAppApiKey
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken?): UserDetails {
        return when (token?.credentials ?: "") {
            todoAppApiKey.userKey
            -> User("User", "", AuthorityUtils.createAuthorityList("USER"))
            todoAppApiKey.adminKey
            -> User("Admin", "", AuthorityUtils.createAuthorityList("ADMIN"))
            else -> throw UsernameNotFoundException("Invalid token is provided from client authentication key")
        }
    }
}

