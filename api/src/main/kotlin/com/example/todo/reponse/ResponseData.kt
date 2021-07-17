package com.example.todo.reponse

data class ResponseData<T>(
    val message: String,
    val data: T
)