package com.example.todo.application.controller

import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.exception.NotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger { }

@RestControllerAdvice
class TodoExceptionHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(e: Exception): ResponseData<String> {
        log.error(e) { "Unexpected Exception is thrown" }
        return ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.name, "Please retry request few minutes later")
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(e: NotFoundException): ResponseData<String> {
        log.info(e) { "not found exception is thrown" }
        return ResponseData(HttpStatus.NOT_FOUND.name, "Please check request parameter is correct.")
    }
}
