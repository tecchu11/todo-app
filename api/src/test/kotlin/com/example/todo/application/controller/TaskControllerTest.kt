package com.example.todo.application.controller

import com.example.todo.application.dto.TaskRegistrationDto
import com.example.todo.application.dto.TaskUpdateDto
import com.example.todo.domain.entity.TaskEntity
import com.example.todo.domain.enumration.TaskStatus
import com.example.todo.domain.exception.NotFoundException
import com.example.todo.domain.service.TaskService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.OffsetDateTime

internal class TaskControllerTest {

    @MockK
    private lateinit var taskService: TaskService

    @InjectMockKs
    private lateinit var taskController: TaskController

    private lateinit var mockMvc: MockMvc

    companion object {
        private const val USER_ID = 1
        private const val BASE_PATH = "/v1/todo"
        private val objectMapper = ObjectMapper()
        private val taskEntity = TaskEntity(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            USER_ID,
            TaskStatus.OPEN,
            OffsetDateTime.now(),
            OffsetDateTime.now()
        )

        private val taskRegistrationDto = TaskRegistrationDto(
            "this is summary",
            "this is description",
            USER_ID,
            TaskStatus.OPEN
        )

        private val taskUpdateDto = TaskUpdateDto(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            USER_ID,
            TaskStatus.OPEN
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockMvc = MockMvcBuilders
            .standaloneSetup(taskController)
            .setControllerAdvice(TodoExceptionHandler::class.java)
            .build()
    }

    @Test
    @DisplayName("Test get request returning  ok or not found")
    fun findAll() {
        val tasks = listOf(taskEntity)

        every { taskService.findAll(USER_ID) }.returns(tasks)
        getTaskList().andExpect(status().isOk)

        every { taskService.findAll(USER_ID) }.throws(NotFoundException("failed"))
        getTaskList().andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Test post request returning  ok")
    fun register() {
        every { taskService.register(any()) }.returns(Unit)
        postTask().andExpect(status().isOk)
    }

    @Test
    @DisplayName("Test put request returning  ok or not found")
    fun update() {
        every { taskService.update(any()) }.returns(Unit)
        putTask().andExpect(status().isOk)

        every { taskService.update(any()) }.throws(NotFoundException("failed"))
        putTask().andExpect(status().isNotFound)
    }

    private fun getTaskList(): ResultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get("$BASE_PATH/$USER_ID")
    )

    private fun postTask(): ResultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .post("$BASE_PATH/")
            .content(objectMapper.writeValueAsString(taskRegistrationDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    )

    private fun putTask(): ResultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .put("$BASE_PATH/")
            .content(objectMapper.writeValueAsString(taskUpdateDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    )
}
