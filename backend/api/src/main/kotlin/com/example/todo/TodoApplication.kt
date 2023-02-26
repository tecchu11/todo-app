package com.example.todo

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition(
    info = Info(
        title = "TODO API",
        description = "API for todo application.",
        version = "v1",
    ),
)
class TodoApplication

fun main(args: Array<String>) {
    runApplication<TodoApplication>(*args)
}
