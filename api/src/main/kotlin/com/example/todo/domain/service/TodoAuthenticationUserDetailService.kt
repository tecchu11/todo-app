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
        val key = token?.credentials ?: ""

        if (key.toString().isEmpty()) throw UsernameNotFoundException("credentials not found from header")

        return when (key) {
            todoAppApiKey.userKey
            -> User("User", "", AuthorityUtils.createAuthorityList(UserRole.USER.name))
            todoAppApiKey.adminKey
            -> User("Admin", "", AuthorityUtils.createAuthorityList(UserRole.USER.name))
            else -> User("NO Auth User", "", AuthorityUtils.NO_AUTHORITIES)
        }
    }
}

