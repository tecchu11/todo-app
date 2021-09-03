package com.example.todo.extentions

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

internal class ExtensionsKtTest {

    @MockK
    lateinit var httpServletRequest: HttpServletRequest

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("Testing status code when handling error")
    fun httpStatus() {
        every { httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) }
            .returns(404)
        assertThat(httpServletRequest.httpStatus())
            .isEqualTo(HttpStatus.NOT_FOUND)

        every { httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) }
            .returns(null)
        assertThat(httpServletRequest.httpStatus())
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
