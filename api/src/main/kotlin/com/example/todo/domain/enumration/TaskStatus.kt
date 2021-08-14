package com.example.todo.domain.enumration

import kotlin.IllegalArgumentException

enum class TaskStatus(val statusId: String?) {

    OPEN("1"), WIP("2"), CLOSE("3");

    companion object {
        fun of(result: String?): TaskStatus = values().find { it.statusId == result }
            ?: throw IllegalArgumentException("statusId = $result is not defined")
    }
}
