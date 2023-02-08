package com.example.todo.application.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

data class AuthUserDetails(
    private val id: String,
    // This is empty-able because bearer token payload has no password.
    private val password: String,
    private val role: String,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = AuthorityUtils.createAuthorityList(role)

    override fun getPassword(): String = password

    override fun getUsername(): String = id

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
