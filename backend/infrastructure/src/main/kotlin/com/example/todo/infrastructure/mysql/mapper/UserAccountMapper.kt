package com.example.todo.infrastructure.mysql.mapper

import com.example.todo.infrastructure.mysql.table.UserTable
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserAccountMapper {

    @Select(
        """
            select
                user_id,
                name,
                email,
                password,
                role
            from
                user_account
            where
                email = #{email}
        """
    )
    fun find(email: String): UserTable?
}
