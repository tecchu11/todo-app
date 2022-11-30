package com.example.todo.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties("todo.api")
data class TaskApiProperty(
    val uri: URI
)
