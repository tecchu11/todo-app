package com.example.todo.presentation.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("todo-app.key")
data class TodoAppApiKey(
    val adminKey: String,
    val userKey: String
)
