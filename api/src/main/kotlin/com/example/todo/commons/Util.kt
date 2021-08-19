package com.example.todo.commons

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

val log = KotlinLogging.logger { }

fun HttpServletRequest.httpStatus(): HttpStatus {
    val code = this.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as? Int?
    return code?.let { HttpStatus.resolve(it) } ?: HttpStatus.INTERNAL_SERVER_ERROR
}
