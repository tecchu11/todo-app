package com.example.todo.mysql.mapper

import com.example.todo.mysql.table.TaskTable
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface TaskMapper {

    @Select(
        """
            select
                id,
                user_id,
                summary,
                description,
                status_id,
                created_at,
                updated_at
            from
                task
            where
                user_id = #{userId}
        """
    )
    fun selectAll(userId: Int): List<TaskTable>

    @Select(
        """
            select
                id,
                user_id,
                summary,
                description,
                status_id,
                created_at,
                updated_at
            from
                task
            where
                user_id = #{userId}
                and id = #{taskId}
        """
    )
    fun selectSingle(@Param("userId") userId: Int, @Param("taskId") taskId: String): TaskTable?

    @Insert(
        """
            insert into task(
                id,
                user_id, 
                summary,
                description,
                status_id,
                created_at,
                updated_at
            )
            values
            (
                #{id},
                #{userId},
                #{summary},
                #{description},
                #{status},
                #{createdAt},
                #{updatedAt}
            )
            ON DUPLICATE KEY UPDATE
                summary = #{summary},
                description = #{description},
                status_id = #{status},
                created_at = #{createdAt},
                updated_at = #{updatedAt}
        """
    )
    fun upsert(task: TaskTable): Boolean

    @Delete(
        """
            delete from task
            where
                user_id = #{userId}
        """
    )
    fun deleteAll(userId: Int)

    @Delete(
        """
            delete from task
            where
                user_id = #{userId}
                and id = #{taskId}
        """
    )
    fun deleteSingle(userId: Int, taskId: String)
}
