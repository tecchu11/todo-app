package com.example.todo.application.configuration

import com.example.todo.domain.service.TodoAuthenticationUserDetailService
import com.example.todo.enums.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

@EnableWebSecurity
class WebSecurityConfiguration(
    private val todoAuthenticationUserDetailService: TodoAuthenticationUserDetailService,
    private val defaultAccessDeniedHandler: DefaultAccessDeniedHandler,
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic().disable()
            .csrf().disable()
            .authorizeHttpRequests()
            .mvcMatchers("/v1/todo/**")?.hasAnyAuthority(UserRole.USER.name, UserRole.ADMIN.name)
            ?.mvcMatchers("/v1/admin/**")?.hasAuthority(UserRole.ADMIN.name)
            ?.mvcMatchers("**", "*")?.denyAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.exceptionHandling()?.accessDeniedHandler(defaultAccessDeniedHandler)
            ?.and()
            ?.addFilter(preAuthenticatedProcessingFilter())
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }

    @Bean
    fun preAuthenticationProvider(): PreAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        .apply {
            setPreAuthenticatedUserDetailsService(todoAuthenticationUserDetailService)
            setUserDetailsChecker(AccountStatusUserDetailsChecker())
        }

    @Bean
    fun authenticationManager(): AuthenticationManager? = authenticationConfiguration.authenticationManager

    @Bean
    fun preAuthenticatedProcessingFilter(): AbstractPreAuthenticatedProcessingFilter =
        TodoApiPreAuthenticationProcessingFilter()
            .apply {
                setAuthenticationManager(authenticationManager())
            }
}
