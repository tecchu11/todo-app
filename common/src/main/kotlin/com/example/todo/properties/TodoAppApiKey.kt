package com.example.todo.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("todo-app.key")
data class TodoAppApiKey(
    val adminKey: String,
    val userKey: String
)
