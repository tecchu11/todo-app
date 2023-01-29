package com.example.todo.exception

/**
 * Base exception class for presentation layer.
 */
open class PresentationException(
    message: String,
    cause: Throwable?
) : RuntimeException(message, cause)
