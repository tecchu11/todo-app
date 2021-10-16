package com.example.todo.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("todo-app.key")
@ConstructorBinding
data class TodoAppApiKey(
    val adminKey: String,
    val userKey: String
)
