package com.example.todo.application.configuration

import org.springframework.http.HttpHeaders
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class TodoApiPreAuthenticationProcessingFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any = ""

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any? {
        val authorizationHeader: String? = request?.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader.isNullOrEmpty()) {
            logger.info("Authorization header is null or empty, so denied access")
            return null
        }
        return authorizationHeader
    }
}
