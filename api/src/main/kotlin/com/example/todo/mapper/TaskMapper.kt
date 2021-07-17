package com.example.todo.mapper

import com.example.todo.model.TaskModel
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TaskMapper {

    fun selectAll(userId: Int): List<TaskModel>

    fun insert(taskModel: TaskModel)

    fun update(taskModel: TaskModel)

}