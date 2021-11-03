package com.example.todo.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConfigurationProperties("todo.api")
@ConstructorBinding
data class TaskApiProperty(
    val uri: URI
)
