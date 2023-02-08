package com.example.todo.domain.auth.vo

/**
 * User role.
 */
enum class UserRole {
    ADMIN,
    USER;

    companion object {
        /**
         * Map of name and enum.
         */
        val roleMapping = UserRole.values().associateBy { it.name }
    }
}
