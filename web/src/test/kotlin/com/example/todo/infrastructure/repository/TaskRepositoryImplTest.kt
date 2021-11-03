package com.example.todo.infrastructure.repository

import com.example.todo.domain.model.TaskModel
import com.example.todo.domain.model.TaskRegistrationModel
import com.example.todo.domain.model.TaskUpdateModel
import com.example.todo.enums.TaskStatus
import com.example.todo.infrastructure.config.TaskApiProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.OffsetDateTime
import java.time.ZoneId

internal class TaskRepositoryImplTest {

    @SpyK
    private var taskApiRestTemplate = RestTemplate()

    @MockK
    private lateinit var taskApiProperty: TaskApiProperty

    @InjectMockKs
    private lateinit var taskRepository: TaskRepositoryImpl

    private lateinit var mockRestServiceServer: MockRestServiceServer

    companion object {
        private const val USER_ID = 1
        private const val BASE_URI = "http://localhost:8080/v1/todo/"
        private val objectMapper = ObjectMapper().apply { this.registerModule(JavaTimeModule()) }
        private val defaultZoneId = ZoneId.of("Asia/Tokyo")
        private val task = TaskModel(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            1,
            TaskStatus.OPEN,
            OffsetDateTime.now(defaultZoneId),
            OffsetDateTime.now(defaultZoneId)
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockRestServiceServer = MockRestServiceServer.createServer(taskApiRestTemplate)
    }

    @Test
    @DisplayName("Test that can call GET:task api")
    fun findAll() {
        val uri = URI.create(BASE_URI)
        every { taskApiProperty.uri }.returns(uri)
        mockRestServiceServer
            .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("$BASE_URI$USER_ID"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(
                MockRestResponseCreators.withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(listOf(task)))
            )

        assertThat(taskRepository.findAll(USER_ID))
            .usingElementComparatorIgnoringFields("registeredAt", "updatedAt")
            .contains(task)
        mockRestServiceServer.verify()
    }

    @Test
    @DisplayName("Test that can call POST:task api")
    fun register() {
        every { taskApiProperty.uri }.returns(URI.create(BASE_URI))
        mockRestServiceServer
            .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(BASE_URI))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andRespond(
                MockRestResponseCreators.withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(task))
            )
        val taskRegistrationModel = TaskRegistrationModel(
            task.taskSummary,
            task.taskDescription,
            task.userId,
            task.status
        )

        assertThat(taskRepository.register(taskRegistrationModel))
            .usingRecursiveComparison()
            .ignoringFields("registeredAt", "updatedAt")
            .isEqualTo(task)
        mockRestServiceServer.verify()
    }

    @Test
    @DisplayName("Test that can call PUT:task api")
    fun update() {
        every { taskApiProperty.uri }.returns(URI.create(BASE_URI))
        mockRestServiceServer
            .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(BASE_URI))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
            .andRespond(
                MockRestResponseCreators.withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(task))
            )
        val taskUpdateModel = TaskUpdateModel(
            task.taskId,
            task.taskSummary,
            task.taskDescription,
            task.userId,
            task.status
        )
        assertThat(taskRepository.update(taskUpdateModel))
            .usingRecursiveComparison()
            .ignoringFields("registeredAt", "updatedAt")
            .isEqualTo(task)
        mockRestServiceServer.verify()
    }
}
