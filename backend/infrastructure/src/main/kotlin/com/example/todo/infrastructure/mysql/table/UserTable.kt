package com.example.todo.infrastructure.mysql.table

data class UserTable(
    val userId: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)
