package com.example.todo.application.reponse

data class ResponseData<T>(
    val message: String,
    val data: T
)
