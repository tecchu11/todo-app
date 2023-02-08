package com.example.todo.domain.auth.entity

/**
 * Indicate credentials.
 */
data class UserCredential(
    var email: String,
    var password: String,
) {
    init {
        require(email.isNotBlank() && password.isNotBlank()) {
            "Email and password are not allowed to be blank."
        }
    }

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
