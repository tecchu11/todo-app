package com.example.todo.extentions

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus

fun HttpServletRequest.httpStatus(): HttpStatus {
    val code = this.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as? Int?
    return code?.let { HttpStatus.resolve(it) } ?: HttpStatus.INTERNAL_SERVER_ERROR
}
