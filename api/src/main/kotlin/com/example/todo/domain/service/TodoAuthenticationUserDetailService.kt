package com.example.todo.domain.service

import com.example.todo.domain.configuration.TodoAppApiKey
import com.example.todo.domain.enumration.UserRole
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service

@Service
class TodoAuthenticationUserDetailService(
    private val todoAppApiKey: TodoAppApiKey
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken?): UserDetails {
        return when (token?.credentials ?: "") {
            todoAppApiKey.userKey
            -> User("User", "", AuthorityUtils.createAuthorityList(UserRole.USER.name))
            todoAppApiKey.adminKey
            -> User("Admin", "", AuthorityUtils.createAuthorityList(UserRole.ADMIN.name))
            else -> User("Invalid User", "", AuthorityUtils.createAuthorityList(UserRole.INVALID.name))
        }
    }
}
