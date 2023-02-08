package com.example.todo.presentation.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse

/**
 * Indicate resource not found.(HttpStatus 500 will be return client).
 *
 * response is used for RFC 7807 field *detail*, message is used logger message.
 */
class ServerException(
    private val response: String,
    message: String,
    cause: Throwable? = null,
) : ErrorResponse, PresentationException(message, cause) {
    override fun getStatusCode(): HttpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR

    override fun getBody(): ProblemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR, response
    )
}
