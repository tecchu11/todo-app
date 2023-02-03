package com.example.todo.auth.entity

/**
 * Indicate credentials.
 */
data class UserCredential(
    var email: String,
    var password: String,
) {
    companion object {

        /**
         * Create UserCredential instance.
         */
        fun from(email: String, password: String): UserCredential = UserCredential(
            email = email,
            password = password,
        )
    }

    override fun toString(): String {
        return "UserCredential(email=**, password=**)"
    }
}
