package com.example.todo.presentation.config.security

import com.example.todo.application.usecase.AuthUserDetailsUseCase
import com.example.todo.domain.auth.vo.UserRole
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Configurations for spring security.
 *
 * This class configure bearer auth.
 *
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        bearerTokenAuthenticationFilter: BearerTokenAuthenticationFilter,
        authenticationProvider: AuthenticationProvider,
        customAccessDeniedHandler: CustomAccessDeniedHandler,
        customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    ): SecurityFilterChain {
        return http
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .addFilterBefore(bearerTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests {
                it.requestMatchers("/v1/users/**").hasAnyAuthority(UserRole.USER.name, UserRole.ADMIN.name)
                it.requestMatchers("/v1/auth/login").permitAll()
                it.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // actuator endpoint
                it.anyRequest().denyAll()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint) // handle 401
                it.accessDeniedHandler(customAccessDeniedHandler) // handle 403
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun authenticationProvider(
        authUserDetailsUseCase: AuthUserDetailsUseCase,
        passwordEncoder: PasswordEncoder,
    ): AuthenticationProvider = DaoAuthenticationProvider().apply {
        this.setUserDetailsService(authUserDetailsUseCase)
        this.setPasswordEncoder(passwordEncoder)
    }
}
