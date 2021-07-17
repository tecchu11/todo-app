package com.example.todo.controller

import com.example.todo.exception.NotFoundException
import com.example.todo.reponse.ResponseData
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException

private val log = KotlinLogging.logger { }

@RestControllerAdvice
class TodoExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(e: NotFoundException): ResponseData<String> {
        log.error("not found exception is thrown", e)
        return ResponseData("Failure", "Please check request parameter is correct.")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalRequestHandler(e: IllegalArgumentException): ResponseData<String> {
        log.error("Request is illegal", e)
        return ResponseData("Failure", "Please check way to request body or parameter.")
    }
}