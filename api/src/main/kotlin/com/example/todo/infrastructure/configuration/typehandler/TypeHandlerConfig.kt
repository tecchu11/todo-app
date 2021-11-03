package com.example.todo.infrastructure.configuration.typehandler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TypeHandlerConfig {

    @Bean
    fun taskStatusTypeHandler(): TaskStatusTypeHandler = TaskStatusTypeHandler()
}
