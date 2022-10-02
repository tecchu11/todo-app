package com.example.todo.application.configuration

import com.example.todo.enums.UserRole
import com.example.todo.properties.TodoAppApiKey
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component

@Component
class TodoAuthenticationUserDetailService(
    private val todoAppApiKey: TodoAppApiKey
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken?): UserDetails {
        return when (token?.credentials) {
            todoAppApiKey.userKey
            -> User("User", "", AuthorityUtils.createAuthorityList(UserRole.USER.name))
            todoAppApiKey.adminKey
            -> User("Admin", "", AuthorityUtils.createAuthorityList(UserRole.ADMIN.name))
            else -> throw UsernameNotFoundException("Deny")
        }
    }
}
