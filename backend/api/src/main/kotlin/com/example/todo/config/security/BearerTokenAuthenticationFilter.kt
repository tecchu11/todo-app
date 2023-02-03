package com.example.todo.config.security

import com.example.todo.service.BearerTokenService
import com.example.todo.type.BearerToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class BearerTokenAuthenticationFilter(
    private val bearerTokenService: BearerTokenService,
) : OncePerRequestFilter() {

    /**
     * Authenticate with bearer token.
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val auth = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (auth.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        val bearerToken = kotlin
            .runCatching { BearerToken.from(auth) }
            .onFailure {
                throw BadCredentialsException("User requested with invalid token", it)
            }.getOrThrow()
        if (SecurityContextHolder.getContext().authentication == null) {
            val payload = bearerTokenService.verify(bearerToken)
            val authUser = User(
                payload.sub,
                "",
                AuthorityUtils.createAuthorityList(payload.role)
            )
            val authentication = UsernamePasswordAuthenticationToken(
                authUser,
                null,
                authUser.authorities
            ).apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
