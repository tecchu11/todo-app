package com.example.todo.presentation.controller.advice

import com.example.todo.presentation.exception.ClientException
import com.example.todo.presentation.exception.ResourceNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Handling exception globally.
 */
@RestControllerAdvice
class TodoExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(TodoExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(
        cause: Exception,
        request: HttpServletRequest,
    ): ProblemDetail {
        log.error("Unexpected Exception is thrown when requested to ${request.requestURI}", cause)
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Please retry to request few minutes later."
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(
        cause: ResourceNotFoundException,
        request: HttpServletRequest,
    ): ProblemDetail {
        log.info("Client request to ${request.requestURI}, but no resources", cause)
        return cause.body
    }

    @ExceptionHandler(ClientException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestHandler(
        cause: ClientException,
        request: HttpServletRequest,
    ): ProblemDetail {
        log.info("Client requested to ${request.requestURI} with invalid parameters", cause)
        return cause.body
    }

    @ExceptionHandler(
        IllegalArgumentException::class,
        ConstraintViolationException::class,
        BadCredentialsException::class,
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestHandler(
        cause: Throwable,
        request: HttpServletRequest,
    ): ProblemDetail {
        log.info("Client requested to ${request.requestURI} with invalid parameters, body or method", cause)
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Invalid request content."
        )
    }
}
