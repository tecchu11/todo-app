package com.example.todo.presentation.config.security

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        customAccessDeniedHandler: CustomAccessDeniedHandler,
        customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
        authorizationHeaderAuthenticationProcessingFilter: AuthorizationHeaderAuthenticationProcessingFilter
    ): SecurityFilterChain {
        http
            .httpBasic().disable()
            .csrf().disable()
            ?.addFilter(authorizationHeaderAuthenticationProcessingFilter)
            ?.authorizeHttpRequests {
                it.requestMatchers("/v1/**")?.hasAnyAuthority(UserRole.USER.name, UserRole.ADMIN.name)
                it.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // actuator endpoint
                it.anyRequest()?.denyAll()
            }
            ?.exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint) // handle 401
                it.accessDeniedHandler(customAccessDeniedHandler) // handle 403
            }
            ?.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }

    @Bean
    fun preAuthenticatedProcessingFilter(
        authenticationConfiguration: AuthenticationConfiguration,
    ): AuthorizationHeaderAuthenticationProcessingFilter = AuthorizationHeaderAuthenticationProcessingFilter()
        .apply {
            this.setAuthenticationManager(authenticationConfiguration.authenticationManager)
        }

    @Bean
    fun preAuthenticatedAuthenticationProvider(
        authorizationHeaderAuthenticationUserDetailService: AuthorizationHeaderAuthenticationUserDetailService,
    ): PreAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        .apply {
            this.setThrowExceptionWhenTokenRejected(true)
            this.setPreAuthenticatedUserDetailsService(authorizationHeaderAuthenticationUserDetailService)
        }
}
