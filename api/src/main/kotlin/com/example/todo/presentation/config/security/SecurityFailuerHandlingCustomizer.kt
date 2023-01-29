package com.example.todo.presentation.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.apply {
            status = HttpStatus.FORBIDDEN.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            outputStream.println(
                objectMapper.writeValueAsString(
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.FORBIDDEN,
                        "Forbidden access because your token are insufficient to grant access."
                    )
                )
            )
        }
    }
}

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        response?.apply {
            status = HttpStatus.UNAUTHORIZED.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            outputStream.println(
                objectMapper.writeValueAsString(
                    ProblemDetail.forStatusAndDetail(
                        HttpStatus.UNAUTHORIZED,
                        "Refused access because your access token lacks valid authentication credential"
                    )
                )
            )
        }
    }
}
