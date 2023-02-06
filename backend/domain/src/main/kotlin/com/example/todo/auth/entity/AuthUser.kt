package com.example.todo.auth.entity

import com.example.todo.auth.vo.UserRole
import com.example.todo.shared.vo.UserId

/**
 * Indicate auth user for this project.
 */
data class AuthUser(
    var userId: UserId,
    var userCredential: UserCredential,
    var role: UserRole,
) {
    companion object {

        /**
         * Create from AuthUser.
         *
         * If passed invalid user parameters, throw [IllegalArgumentException].
         */
        fun from(userId: Int, email: String, password: String, role: String) = AuthUser(
            userId = UserId.from(userId),
            userCredential = UserCredential.from(email, password),
            role = UserRole.valueOf(role)
        )
    }
}
