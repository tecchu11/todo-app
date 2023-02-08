package com.example.todo.repository

import com.example.todo.domain.auth.AuthUserRepository
import com.example.todo.domain.auth.entity.AuthUser
import com.example.todo.domain.auth.vo.UserRole
import com.example.todo.mysql.mapper.UserAccountMapper
import org.springframework.stereotype.Repository

/**
 * CRUD for auth user.
 */
@Repository
class AuthUserRepositoryImpl(
    private val userAccountMapper: UserAccountMapper,
) : AuthUserRepository {

    /**
     * Find authenticated user by email.
     *
     * If user is not found or user have undefined role, returning null.
     */
    override fun find(email: String): AuthUser? {
        return userAccountMapper.find(email)?.let { userTable ->
            UserRole.roleMapping[userTable.role]?.let {
                AuthUser.from(
                    userId = userTable.userId,
                    email = userTable.email,
                    password = userTable.password,
                    role = userTable.role,
                )
            }
        }
    }
}
