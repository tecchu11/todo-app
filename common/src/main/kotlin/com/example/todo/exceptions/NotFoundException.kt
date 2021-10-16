package com.example.todo.exceptions

import kotlin.RuntimeException

class NotFoundException(message: String, throwable: Throwable?) : RuntimeException(message, throwable) {
    constructor(message: String) : this(message, null)
}
