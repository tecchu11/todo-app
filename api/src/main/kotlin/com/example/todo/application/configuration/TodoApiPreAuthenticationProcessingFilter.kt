package com.example.todo.application.configuration

import org.springframework.http.HttpHeaders
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class TodoApiPreAuthenticationProcessingFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any = ""

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any =
        request?.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
}
