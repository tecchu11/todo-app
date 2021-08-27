package com.example.todo.application.handler

import com.example.todo.application.reponse.ResponseData
import com.example.todo.domain.exception.NotFoundException
import com.example.todo.extentions.log
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TodoExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(e: NotFoundException): ResponseData<String> {
        log.info("not found exception is thrown", e)
        return ResponseData(HttpStatus.NOT_FOUND.name, "Please check request parameter is correct.")
    }
}
