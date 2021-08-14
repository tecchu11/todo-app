package com.example.todo.infrastructure.mapper

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TaskMapper {

    fun selectAll(userId: Int): List<TaskEntity>

    fun insert(task: Task)

    fun update(task: Task): Boolean
}
