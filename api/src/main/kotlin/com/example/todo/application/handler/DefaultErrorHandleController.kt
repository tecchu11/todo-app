package com.example.todo.application.handler

import com.example.todo.application.reponse.ResponseData
import com.example.todo.extentions.httpStatus
import mu.KotlinLogging
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.RequestDispatcher.ERROR_REQUEST_URI
import javax.servlet.RequestDispatcher.ERROR_STATUS_CODE
import javax.servlet.http.HttpServletRequest

private val log = KotlinLogging.logger { }

@RestController
@RequestMapping("/error")
class DefaultErrorHandleController : ErrorController {

    @RequestMapping
    fun handleError(request: HttpServletRequest): ResponseEntity<ResponseData<String>> {
        val httpStatus = request.httpStatus()
        val message = when (httpStatus) {
            HttpStatus.NOT_FOUND, HttpStatus.BAD_REQUEST
            -> "Request is not correct. Please confirm request method and parameter, body"
            HttpStatus.UNAUTHORIZED -> "Request is not allowed. Please check your credentials."
            else -> "Something happened on the server. Please retry to request after few minute."
        }
        log.warn(
            "Requested is failed. status code:${request.getAttribute(ERROR_STATUS_CODE)}, " +
                "path: ${request.getAttribute(ERROR_REQUEST_URI)} "
        )
        return ResponseEntity
            .status(httpStatus)
            .body(ResponseData(message, httpStatus.name))
    }
}
