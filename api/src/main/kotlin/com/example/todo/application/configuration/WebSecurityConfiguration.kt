package com.example.todo.application.configuration

import com.example.todo.enums.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

@EnableWebSecurity
class WebSecurityConfiguration {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        defaultAccessDeniedHandler: DefaultAccessDeniedHandler,
        defaultAuthenticationEntryPont: DefaultAuthenticationEntryPont,
        preAuthenticatedProcessingFilter: AbstractPreAuthenticatedProcessingFilter
    ): SecurityFilterChain {
        http
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.mvcMatchers("/v1/todo/**")?.hasAnyAuthority(UserRole.USER.name, UserRole.ADMIN.name)
                it.mvcMatchers("/v1/admin/**")?.hasAuthority(UserRole.ADMIN.name)
                it.mvcMatchers("**", "*")?.denyAll()
                it.anyRequest()?.authenticated()
            }
            ?.exceptionHandling {
                it.accessDeniedHandler(defaultAccessDeniedHandler)
                it.authenticationEntryPoint(defaultAuthenticationEntryPont)
            }
            ?.addFilter(preAuthenticatedProcessingFilter)
            ?.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }

    @Bean
    fun preAuthenticationProvider(
        todoAuthenticationUserDetailService: TodoAuthenticationUserDetailService
    ): PreAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        .apply {
            setPreAuthenticatedUserDetailsService(todoAuthenticationUserDetailService)
        }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager? = authenticationConfiguration.authenticationManager

    @Bean
    fun preAuthenticatedProcessingFilter(
        authenticationManager: AuthenticationManager
    ): AbstractPreAuthenticatedProcessingFilter = TodoApiPreAuthenticationProcessingFilter()
        .apply {
            setAuthenticationManager(authenticationManager)
        }
}
