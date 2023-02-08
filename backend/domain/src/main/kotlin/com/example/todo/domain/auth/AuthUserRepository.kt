package com.example.todo.domain.auth

import com.example.todo.domain.auth.entity.AuthUser

/**
 * CRUD for auth user.
 */
interface AuthUserRepository {

    /**
     * Find by user email.
     */
    fun find(email: String): AuthUser?
}
