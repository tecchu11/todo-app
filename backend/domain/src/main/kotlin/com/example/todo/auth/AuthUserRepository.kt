package com.example.todo.auth

import com.example.todo.auth.entity.AuthUser

/**
 * CRUD for auth user.
 */
interface AuthUserRepository {

    /**
     * Find by user email.
     */
    fun find(email: String): AuthUser?
}
