package com.example.todo.domain.exception

import kotlin.RuntimeException

class NotFoundException(message: String) : RuntimeException(message)
