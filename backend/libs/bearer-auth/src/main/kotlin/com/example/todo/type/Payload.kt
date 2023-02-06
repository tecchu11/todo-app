package com.example.todo.type

/**
 * Available jwt payload.
 */
data class Payload(
    val iss: String,
    val sub: String,
    val role: String,
    val extra: Map<String, String> = emptyMap(),
)
