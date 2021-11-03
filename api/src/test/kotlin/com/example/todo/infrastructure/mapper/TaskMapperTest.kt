package com.example.todo.infrastructure.mapper

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import com.example.todo.enums.TaskStatus
import com.example.todo.infrastructure.configuration.MysqlContainerExtensions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MysqlContainerExtensions::class)
internal class TaskMapperTest {

    @Autowired
    lateinit var taskMapper: TaskMapper

    companion object {
        private const val USER_ID = 1
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        private val systemZoneOffSet = ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())

        private val initialData = TaskEntity(
            "AAAAAAAAAAAAABBBBBBBBBBBBB",
            "test todo task",
            null,
            1,
            TaskStatus.OPEN,
            OffsetDateTime.of(LocalDateTime.parse("2021-01-01 12:00:00", formatter), systemZoneOffSet),
            OffsetDateTime.of(LocalDateTime.parse("2021-01-01 13:00:00", formatter), systemZoneOffSet)
        )

        private val task1 = Task(
            "AAAAAAAAAAAAAAAAAAAAAAAAAA",
            "this is summary",
            "this is description",
            1,
            TaskStatus.OPEN
        )
        private val task2 = Task(
            "AAAAAAAAAAAAABBBBBBBBBBBBB",
            "this is summary",
            "work in progress",
            1,
            TaskStatus.WIP
        )
    }

    @Test
    @DisplayName("Test that can get tasks from task table")
    fun selectAll() {
        assertThat(taskMapper.selectAll(USER_ID))
            .isEqualTo(listOf(initialData))
    }

    @Test
    @DisplayName("Test that can insert task to task table")
    fun insert() {
        taskMapper.insert(task1)
        assertThat(taskMapper.selectAll(task1.userId))
            .usingElementComparatorIgnoringFields("registeredAt", "updatedAt")
            .contains(task1.toEntity(), initialData)
    }

    @Test
    @DisplayName("Test that can update task by task id and userId")
    fun update() {
        taskMapper.update(task2)
        assertThat(taskMapper.selectAll(task2.userId))
            .usingElementComparatorIgnoringFields("registeredAt", "updatedAt")
            .contains(task2.toEntity())
            .doesNotContain(initialData)
    }

    private fun Task.toEntity() = TaskEntity(
        this.taskId,
        this.taskSummary,
        this.taskDescription,
        this.userId,
        this.status,
        OffsetDateTime.now(),
        OffsetDateTime.now()
    )
}
