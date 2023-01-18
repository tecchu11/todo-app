package com.example.todo.domain.enumration

enum class TaskStatus(private val statusId: String) : GenericEnum {

    OPEN("1"), WIP("2"), CLOSE("3");

    override fun code(): String = statusId
}
