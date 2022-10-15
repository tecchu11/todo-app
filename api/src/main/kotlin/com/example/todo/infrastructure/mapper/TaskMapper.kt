package com.example.todo.infrastructure.mapper

import com.example.todo.domain.entity.Task
import com.example.todo.domain.entity.TaskEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface TaskMapper {

    @Select(
        """
            select
                task_id,
                task_summary,
                task_description,
                user_id,
                status_id,
                registered_at,
                updated_at
            from
                tasks
            where
                user_id = #{userId}
        """
    )
    fun selectAll(userId: Int): List<TaskEntity>

    @Insert(
        """
            insert into tasks(
                task_id,
                task_summary,
                task_description,
                user_id,
                status_id
            )
            values
            (
                #{taskId},
                #{taskSummary},
                #{taskDescription},
                #{userId},
                #{status}
            )
        """
    )
    fun insert(task: Task)

    @Update(
        """
        update tasks
        set
            task_summary = #{taskSummary},
            task_description = #{taskDescription},
            status_id = #{status}
        where
            user_id = #{userId}
            and task_id = #{taskId}
        """
    )
    fun update(task: Task): Boolean
}
