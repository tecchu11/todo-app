package com.example.todo.presentation.config.security

import com.example.todo.application.dto.AuthUserDetails
import com.example.todo.bearerauth.service.BearerTokenService
import com.example.todo.bearerauth.type.BearerToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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
            .getOrElse { throw BadCredentialsException("User requested with invalid token", it) }
        if (SecurityContextHolder.getContext().authentication == null) {
            val payload = bearerTokenService.verify(bearerToken)
            val authUserDetails = AuthUserDetails(
                id = payload.sub,
                password = "",
                role = payload.role,
            )
            val authentication = UsernamePasswordAuthenticationToken(
                authUserDetails,
                null,
                authUserDetails.authorities
            ).apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
