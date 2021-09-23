package com.example.todo.application.configuration

import com.example.todo.domain.enumration.UserRole
import com.example.todo.domain.service.TodoAuthenticationUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http
            ?.httpBasic()?.disable()
            ?.csrf()?.disable()
            ?.authorizeRequests()
            ?.antMatchers("/v1/todo**")?.hasAnyRole(UserRole.USER.name, UserRole.ADMIN.name)
            ?.antMatchers("/admin/**")?.hasRole(UserRole.ADMIN.name)
            ?.anyRequest()
            ?.authenticated()
            ?.and()
            ?.addFilter(preAuthenticatedProcessingFilter())
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    @Bean
    fun preAuthenticationProvider(
        todoAuthenticationUserDetailService: TodoAuthenticationUserDetailService
    ): PreAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        .apply {
            setPreAuthenticatedUserDetailsService(todoAuthenticationUserDetailService)
            setUserDetailsChecker(AccountStatusUserDetailsChecker())
        }

    @Bean
    fun preAuthenticatedProcessingFilter(): AbstractPreAuthenticatedProcessingFilter =
        TodoApiPreAuthenticationProcessingFilter()
            .apply {
                setAuthenticationManager(authenticationManager())
            }
}
