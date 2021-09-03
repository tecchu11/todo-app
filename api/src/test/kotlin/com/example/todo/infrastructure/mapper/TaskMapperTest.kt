package com.example.todo.infrastructure.mapper

import com.example.todo.domain.entity.Task
import com.example.todo.domain.enumration.TaskStatus
import com.example.todo.infrastructure.configuration.MysqlContainerExtensions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MysqlContainerExtensions::class)
internal class TaskMapperTest {

    @Autowired
    lateinit var taskMapper: TaskMapper

    companion object {
        private const val userId = 1
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
        val tasks = taskMapper.selectAll(userId)

        assertThat(tasks).isNotEmpty
    }

    @Test
    @DisplayName("Test that can insert task to task table")
    fun insert() {
        taskMapper.insert(task1)
    }

    @Test
    @DisplayName("Test that can update task by task id and userId")
    fun update() {
        val bool = taskMapper.update(task2)
        assertThat(bool).isEqualTo(true)
    }
}
