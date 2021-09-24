package com.example.todo.domain.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("todo-app.key")
@ConstructorBinding
data class TodoAppApiKey(
    val adminKey: String,
    val userKey: String
)
