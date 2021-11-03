package com.example.todo.infrastructure.config

import com.example.todo.properties.TodoAppApiKey
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate
import java.time.Duration
import kotlin.properties.Delegates

@Configuration
class BeanConfig(
    private val todoAppApiKey: TodoAppApiKey
) {
    @Bean
    @ConfigurationProperties("spring.rest-template.default")
    fun defaultTimeoutProperty(): RestTemplateTimeoutProperty = RestTemplateTimeoutProperty()

    @Bean
    fun taskApiRestTemplate(
        defaultTimeoutProperty: RestTemplateTimeoutProperty
    ): RestTemplate = RestTemplateBuilder()
        .setReadTimeout(Duration.ofMillis(defaultTimeoutProperty.readTimeout))
        .setConnectTimeout(Duration.ofMillis(defaultTimeoutProperty.connectTimeout))
        .defaultHeader(HttpHeaders.AUTHORIZATION, todoAppApiKey.userKey)
        .build()

    class RestTemplateTimeoutProperty {
        var readTimeout by Delegates.notNull<Long>()
        var connectTimeout by Delegates.notNull<Long>()
    }
}
