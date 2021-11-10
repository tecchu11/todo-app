package com.example.todo.enums

enum class TaskStatus(private val statusId: String) : GenericEnum {

    OPEN("1"), WIP("2"), CLOSE("3");

    override fun code(): String = statusId
}
