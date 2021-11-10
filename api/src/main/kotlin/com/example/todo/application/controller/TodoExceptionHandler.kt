package com.example.todo.application.controller

import com.example.todo.application.reponse.ResponseData
import com.example.todo.exceptions.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TodoExceptionHandler {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(e: Exception): ResponseData<String> {
        log.error("Unexpected Exception is thrown", e)
        return ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.name, "Please retry request few minutes later")
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(e: NotFoundException): ResponseData<String> {
        log.info("not found exception is thrown", e)
        return ResponseData(HttpStatus.NOT_FOUND.name, "Please check request parameter is correct.")
    }
}
