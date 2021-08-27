package com.example.todo.domain.service

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import com.example.todo.domain.enumration.TaskStatus
import com.example.todo.domain.exception.NotFoundException
import com.example.todo.infrastructure.mapper.TaskMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class TaskServiceTest {

    @MockK
    private lateinit var taskMapper: TaskMapper

    @InjectMockKs
    private lateinit var taskService: TaskService

    companion object {
        private const val userId = 1
        private val task = Task(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            1,
            TaskStatus.OPEN
        )
        private val taskEntity1 = TaskEntity(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            1,
            TaskStatus.OPEN,
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("Test that can get tasks and whether handled exception")
    fun findAll() {
        every { taskMapper.selectAll(userId) }.returns(listOf(taskEntity1))
        assertThat(taskService.findAll(userId)).isNotEmpty

        every { taskMapper.selectAll(userId) }.returns(emptyList())
        assertThatThrownBy { taskService.findAll(userId) }
            .isInstanceOf(NotFoundException::class.java)
    }

    @Test
    @DisplayName("Test that can register task")
    fun register() {
        every { taskMapper.insert(task) }.returns(Unit)
        taskService.register(task)
        verify { taskMapper.insert(task) }
    }

    @Test
    @DisplayName("Test that can update task and whether handled exception")
    fun update() {
        every { taskMapper.update(task) }.returns(true)
        taskService.update(task)
        verify { taskMapper.update(task) }

        every { taskMapper.update(task) }.returns(false)
        assertThatThrownBy { taskService.update(task) }
            .isInstanceOf(NotFoundException::class.java)
    }
}